package happiness.meter

import org.apache.spark.{SparkContext, SparkConf}
import com.datastax.spark.connector._

object HappinessMeterAnalyzer {
  val Scala = "scala"
  val Java = "java"
  val sparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("Happiness Meter Spark App Analyzer")
    .set("spark.logConf", "true")
    .set("spark.driver.port", "7778")
    .set("spark.driver.host", "localhost")
    .set("spark.akka.logLifecycleEvents", "true")
    .set("spark.cassandra.connection.host", "127.0.0.1")

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "c:\\winutil\\")
    val sparkContext = new SparkContext(sparkConf)

    val tweets = sparkContext
      .cassandraTable("ks", "twitter")
      .map(row => (row.getString("tweet_class"), row.getBoolean("tweet_happy")))
      .countByValue()

    val scalaHappyTweets = tweets((Scala, true))
    val scalaUnhappyTweets = tweets((Scala, false))
    val javaHappyTweets = tweets((Java, true))
    val javaUnhappyTweets = tweets((Java, false))

    val scalaSatisfactionPercentage = roundBy2((scalaHappyTweets * 100.0) / (scalaHappyTweets + scalaUnhappyTweets))
    val javaSatisfactionPercentage = roundBy2((javaHappyTweets * 100.0) / (javaHappyTweets + javaUnhappyTweets))

    println(
      s"""
        |Java vs Scala satisfaction twitter model (~ 2 hours of manning)
        |Total tweets = ${scalaHappyTweets + scalaUnhappyTweets + javaHappyTweets + javaUnhappyTweets}
        |Scala satisfaction = $scalaSatisfactionPercentage%
        |Java satisfaction = $javaSatisfactionPercentage%
      """.stripMargin)

    sparkContext.stop()
  }

  def roundBy2(x: Double) = Math.round(x * 100) / 100.0
}
