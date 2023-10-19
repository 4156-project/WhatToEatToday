package com.whattoeattoday.recommendationservice.recommendation;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.stat.Correlation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */
public class demo {
    public static void tfidf() {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.master", "local")
                .getOrCreate();

//        List<Row> data = Arrays.asList(
//                RowFactory.create(0, "Hi I heard about Spark"),
//                RowFactory.create(1, "I wish Java could use case classes"),
//                RowFactory.create(2, "Logistic regression models are neat"),
//                RowFactory.create(3, "I'm about to hear Spark")
//        );

        String jdbcUrl = "jdbc:mysql://104.198.225.31/test?useSSL=false";
        String username = "root";
        String password = "031805";
        String tableName = "food";

        // 读取数据
        Dataset<Row> mysqlData = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", tableName)
                .option("user", username)
                .option("password", password)
                .load();
        Dataset<Row> foodData = mysqlData.select("id", "title");


//        StructType schema = new StructType(new StructField[]{
//                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
//                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
//        });
//        Dataset<Row> sentenceData = spark.createDataFrame(data, schema);

        Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(foodData);

        int numFeatures = 20;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);

        Dataset<Row> featurizedData = hashingTF.transform(wordsData);
        // alternatively, CountVectorizer can also be used to get term frequency vectors

        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);

        Dataset<Row> rescaledData = idfModel.transform(featurizedData);
        rescaledData.select("id", "features").show();
        Dataset<Row> select = rescaledData.select("id", "features");
        select.createOrReplaceTempView("people");
        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people WHERE id=0");
        List<Row> rows = sqlDF.collectAsList();
        Vector vector = (Vector) rows.get(0).get(1);

        select.foreach(row -> {
            System.out.println(row.get(0));
            Vector curVector = (Vector) row.get(1);
            double sqdist = Vectors.sqdist(vector, curVector);
            System.out.println(sqdist);
        });

    }

    public static void main(String[] args) {
        tfidf();
    }
}
