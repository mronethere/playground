import sbt._

object Dependencies {
  lazy val akkaVersion = "2.4.0"
  lazy val specs2Version = "3.6.4"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val specs2core = "org.specs2" %% "specs2-core" % specs2Version

  val commonDeps = Seq(akkaActor, akkaSlf4j, akkaTestkit % Test, specs2core % Test)
}
