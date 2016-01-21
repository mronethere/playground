import sbt._

object Dependencies {
  lazy val akkaVersion = "2.4.0"
  lazy val sprayVersion = "1.3.3"
  lazy val specs2Version = "3.6.4"
  lazy val sparkVersion = "1.6.0"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  val sprayCan = "io.spray" %% "spray-can" % sprayVersion
  val sprayRouting = "io.spray" %% "spray-routing" % sprayVersion
  val sprayTestkit = "io.spray" %% "spray-testkit" % sprayVersion

  val specs2core = "org.specs2" %% "specs2-core" % specs2Version

  val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkStreaming = "org.apache.spark" %% "spark-streaming" % sparkVersion
  val sparkStreamingTwitter = "org.apache.spark" %% "spark-streaming-twitter" % sparkVersion
  val sparkMLlib = "org.apache.spark" %% "spark-mllib" % sparkVersion
  val sparkCassandra = "com.datastax.spark" %% "spark-cassandra-connector" % "1.5.0-RC1"

  val commonDeps = Seq(akkaActor, akkaSlf4j, akkaTestkit % Test, specs2core % Test)
  val sprayDeps = Seq(sprayCan, sprayRouting, sprayTestkit % Test)
  val sparkDeps = Seq(sparkCore, sparkStreaming, sparkStreamingTwitter, sparkMLlib, sparkCassandra)
}
