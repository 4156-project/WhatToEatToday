package com.whattoeattoday.recommendationservice.demo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */

object Demo {
  def printDemo(str:String) = {
    println(str)
  }

  def computeSimilarity(request: GetSimilarityRequest)={
    val conf = new SparkConf().setMaster("local").setAppName("SparkReadMysql")
    val sparkSession = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    val tableName = request.tableName

    val prop = scala.collection.mutable.Map[String, String]()
    prop.put("user", "root")
    prop.put("password", "031805")
    prop.put("driver", "com.mysql.cj.jdbc.Driver")
    prop.put("dbtable", tableName)
    prop.put("url", "jdbc:mysql://104.198.225.31/test?useSSL=false")

    val df = sparkSession.read.format("jdbc").options(prop).load()
    val viewName = tableName

    df.createOrReplaceTempView(tableName)
    var sql = "select"
    var fieldNames = " "
    request.features.foreach(feature => {
      fieldNames += feature+", "
    })
    fieldNames = fieldNames.substring(0, fieldNames.length-2)
    sql += fieldNames+" from "+tableName
    val featureDF = sparkSession.sql(sql)
    val rdd: RDD[Row] = featureDF.rdd
    val rddSeq: RDD[Seq[String]] = rdd.filter(row => row.get(0) != null).map(row => {
      row.getString(0).split(" ").toSeq})

    println("----------------------------------------")
    rddSeq.foreach(seq => println(seq))
    println("----------------------------------------")

    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(rddSeq)

    // While applying HashingTF only needs a single pass to the data, applying IDF needs two passes:
    // First to compute the IDF vector and second to scale the term frequencies by IDF.
    tf.cache()
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)
    println("----------------------------------------")
    tfidf.foreach(vec => println(vec))
    println("----------------------------------------")

    val word2vec = new Word2Vec()
    val model = word2vec.fit(rddSeq)
    val synonyms = model.findSynonyms("Beef", 10)
    for ((synonym, cosineSimilarity) <- synonyms) {
      println("----------------------------------------")
      println(s"$synonym $cosineSimilarity")
    }

//    // spark.mllib IDF implementation provides an option for ignoring terms which occur in less than
//    // a minimum number of documents. In such cases, the IDF for these terms is set to 0.
//    // This feature can be used by passing the minDocFreq value to the IDF constructor.
//    val idfIgnore = new IDF(minDocFreq = 2).fit(tf)
//    val tfidfIgnore: RDD[Vector] = idfIgnore.transform(tf)
  }

  def main(args: Array[String]): Unit = {
    val request: GetSimilarityRequest = new GetSimilarityRequest {
      override val tableName: String = "food"
      override val contentId: String = "1"
      override val features: List[String] = List("title")
    }
    computeSimilarity(request)
  }
}
