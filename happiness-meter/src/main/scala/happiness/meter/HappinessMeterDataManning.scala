package happiness.meter

import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

object HappinessMeterDataManning {
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
      .map(row => (row.getString("tweet_class"), row.getString("tweet_text")))
      .filter {
        case (clazz, text) => !text.contains("tea") && !text.contains("hiring") && !text.contains("offer")
            !text.toLowerCase.contains("jobsearch".toLowerCase)
      }.cache()

    println(s"total count: ${tweets.count()}")

    tweets foreach println

    sparkContext.stop()
  }

  def roundBy2(x: Double) = Math.round(x * 100) / 100.0
}
