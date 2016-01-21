package happiness.meter

import com.typesafe.config.ConfigFactory
import org.apache.log4j.{Logger, Level}
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import com.datastax.spark.connector._
import com.datastax.spark.connector.streaming._

object HappinessMeterApplication {
  val Scala = "scala"
  val Java = "java"
  val twitterConfig = ConfigFactory.load("twitter")
  val sparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("Happiness Meter Spark App")
    .set("spark.logConf", "true")
    .set("spark.driver.port", "7777")
    .set("spark.driver.host", "localhost")
    .set("spark.akka.logLifecycleEvents", "true")
    .set("spark.cassandra.connection.host", "127.0.0.1")

  def main(args: Array[String]): Unit = {
    System.setProperty("twitter4j.oauth.consumerKey", twitterConfig.getString("consumerKey"))
    System.setProperty("twitter4j.oauth.consumerSecret", twitterConfig.getString("consumerSecret"))
    System.setProperty("twitter4j.oauth.accessToken", twitterConfig.getString("accessToken"))
    System.setProperty("twitter4j.oauth.accessTokenSecret", twitterConfig.getString("accessTokenSecret"))
    System.setProperty("hadoop.home.dir", "c:\\winutil\\")

    val sparkContext = new SparkContext(sparkConf)
    val streamingContext = new StreamingContext(sparkContext, Seconds(5))

    val happy = sparkContext.textFile("happiness-meter/src/main/resources/happy.txt")
    val unhappy = sparkContext.textFile("happiness-meter/src/main/resources/unhappy.txt")
    val tf = new HashingTF(numFeatures = 10000)
    val happyFeatures = happy.map(text => tf.transform(text.split(" ")))
    val unhappyFeatures = unhappy.map(text => tf.transform(text.split(" ")))
    val happyExamples = happyFeatures.map(features => LabeledPoint(1, features))
    val unhappyExamples = unhappyFeatures.map(features => LabeledPoint(0, features))
    val trainingData = happyExamples.union(unhappyExamples)
    trainingData.cache()
    val model = new LogisticRegressionWithSGD().run(trainingData)

    val stream = TwitterUtils
      .createStream(streamingContext, None, List(Scala, Java))
      .filter(_.getLang == "en")
      .flatMap { status =>
        val clazz = classifyText(status.getText)
        if (clazz.isEmpty) Nil
        else List((status.getId, status.getText, clazz,
            model.predict(tf.transform(status.getText.split(" "))) > 0.5))
      }

    stream.saveToCassandra("ks", "twitter", SomeColumns("tweet_id", "tweet_text", "tweet_class", "tweet_happy"))

    streamingContext.start()
    streamingContext.awaitTermination()
  }

  def classifyText(text: String): String = {
    val textLC = text.toLowerCase
    val containsScala = textLC.contains(Scala)
    val containsJava = textLC.contains(Java)
    if ((containsScala && containsJava) || !(containsScala || containsJava)) ""
    else if (containsScala) Scala else Java
  }
}
