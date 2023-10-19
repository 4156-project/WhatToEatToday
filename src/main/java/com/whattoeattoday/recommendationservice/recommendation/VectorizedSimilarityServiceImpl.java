package com.whattoeattoday.recommendationservice.recommendation;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankRequest;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@ConfigurationProperties(prefix = "spring")
@Service
public class VectorizedSimilarityServiceImpl implements VectorizedSimilarityService{

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private static SparkSession spark;
    @Override
    public BaseResponse<List<Row>> getVectorizedSimilarityRank(GetVectorizedSimilarityRankRequest request) {
        // TODO Param Validation
        String tableName = request.getCategoryName();

        spark = SparkSession
                .builder()
                .appName("Get Vectorized Similarity Rank")
                .config("spark.master", "local")
                .getOrCreate();

        Dataset<Row> mysqlData = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", tableName)
                .option("user", username)
                .option("password", password)
                .load()
                .select("id", request.getFieldName())
                .where(String.format("%s is not null", request.getFieldName()));

        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
        });

        Dataset<Row> sentenceData = spark.createDataFrame(mysqlData.toDF().toJavaRDD(), schema);

        Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(sentenceData);

        int numFeatures = 100;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);
        Dataset<Row> featurizedData = hashingTF.transform(wordsData);
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);

        Dataset<Row> select = rescaledData.select("id", "features", "sentence");
        select.createOrReplaceTempView("view");
        Dataset<Row> targetData = spark.sql(String.format("SELECT * FROM view WHERE id = %s", request.getTargetId()));
        Vector targetVector = (Vector) targetData.collectAsList().get(0).get(1);

        PriorityQueue<Row> topQueue = new PriorityQueue<>(new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {
                return (int)((double) o2.get(1) - (double) o1.get(1));
            }
        });

        List<Row> rowsList = select.collectAsList();
        for (Row row : rowsList) {
            Vector curVector = (Vector) row.get(1);
            double sqdist = Vectors.sqdist(targetVector, curVector);
            Row resultRow = RowFactory.create(row.get(0), sqdist, row.get(2));
            topQueue.add(resultRow);
            if (topQueue.size() > request.getRankTopSize()) {
                topQueue.poll();
            }
        }

        List<Row> resultList = new ArrayList<>();
        while (!topQueue.isEmpty()) {
            resultList.add(0, topQueue.poll());
        }

        spark.close();
        return BaseResponse.with(Status.SUCCESS, resultList);
    }
}
