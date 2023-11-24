package com.whattoeattoday.recommendationservice.recommendation;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
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

    private static SparkSession spark = SparkSession
            .builder()
            .appName("Get Vectorized Similarity Rank")
            .config("spark.master", "local")
            .getOrCreate();

    @Override
    public BaseResponse<List<Row>> getVectorizedSimilarityRank(GetVectorizedSimilarityRankRequest request) {
        // TODO Param Validation
        String tableName = request.getCategoryName();

        Dataset<Row> sentenceData = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", tableName)
                .option("user", username)
                .option("password", password)
                .load()
                .where(String.format("%s is not null", request.getFieldName()));

        Tokenizer tokenizer = new Tokenizer().setInputCol(request.getFieldName()).setOutputCol("words");
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

        Dataset<Row> select = rescaledData.select("id", "features", request.getFieldName());
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
            Vector curVector = row.getAs("features");
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

        return BaseResponse.with(Status.SUCCESS, resultList);
    }

    @Override
    public BaseResponse<List<Row>> getVectorizedSimilarityRankOnMultiField(GetVectorizedSimilarityRankOnMultiFieldRequest request) {
        // TODO Param Check
        // TODO Double Type Unsupported
        String tableName = request.getCategoryName();
        String[] fieldNameArr = request.getFieldNameList().toArray(new String[]{});
        String[] sqls = new String[fieldNameArr.length];
        for (int i = 0; i < fieldNameArr.length; i++) {
            sqls[i] = String.format("%s is not null", fieldNameArr[i]);
        }
        String fieldNames = String.join(" AND ", sqls);
        Dataset<Row> sentenceData = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", tableName)
                .option("user", username)
                .option("password", password)
                .load()
                .where(fieldNames);

        Tokenizer tokenizer = null;
        Dataset<Row> wordsData = null;
        Dataset<Row> featurizedData = null;
        int numFeatures = 100;
        HashingTF hashingTF = null;
        IDF idf = null;
        IDFModel idfModel = null;
//        Dataset<Row> rescaledData = null;
        for (int i = 0; i < fieldNameArr.length; i++) {
            String fieldName = fieldNameArr[i];
            tokenizer = new Tokenizer().setInputCol(fieldName).setOutputCol("words"+i);
            wordsData = tokenizer.transform(sentenceData);
            hashingTF = new HashingTF()
                    .setInputCol("words"+i)
                    .setOutputCol("rawFeatures"+i)
                    .setNumFeatures(numFeatures);
            featurizedData = hashingTF.transform(wordsData);
            idf = new IDF().setInputCol("rawFeatures"+i).setOutputCol("features"+i);
            idfModel = idf.fit(featurizedData);
            sentenceData = idfModel.transform(featurizedData);
        }
        sentenceData.show();
        Vector[] targetVectors = new Vector[fieldNameArr.length];
        for (int i = 0; i < fieldNameArr.length; i++) {
            targetVectors[i] = sentenceData
                    .where(String.format("id = %s", request.targetId))
                    .collectAsList()
                    .get(0)
                    .getAs("features"+i);
        }
        PriorityQueue<Row> topQueue = new PriorityQueue<>(new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {
                return (int)((double) o2.get(1) - (double) o1.get(1));
            }
        });

        List<Row> rowsList = sentenceData.collectAsList();
        for (Row row : rowsList) {
            Vector[] curVectors = new Vector[fieldNameArr.length];
            double sqdist = 0;
            for (int i = 0; i < fieldNameArr.length; i++) {
                curVectors[i] = row.getAs("features"+i);
                sqdist += Vectors.sqdist(targetVectors[i], curVectors[i]);
            }

            Row resultRow = RowFactory.create(row.getAs("id"), sqdist);
            topQueue.add(resultRow);
            if (topQueue.size() > request.getRankTopSize()) {
                topQueue.poll();
            }
        }


        List<Row> resultList = new ArrayList<>();
        while (!topQueue.isEmpty()) {
            resultList.add(0, topQueue.poll());
        }

        return BaseResponse.with(Status.SUCCESS, resultList);

    }
}
