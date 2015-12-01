import sbt._

object Dependencies {
  lazy val akkaVersion = "2.4.0"
  lazy val sprayVersion = "1.3.3"
  lazy val specs2Version = "3.6.4"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val sprayCan = "io.spray" %% "spray-can" % sprayVersion
  val sprayRouting = "io.spray" %% "spray-routing" % sprayVersion
  val sprayTestkit = "io.spray" %% "spray-testkit" % sprayVersion
  val specs2core = "org.specs2" %% "specs2-core" % specs2Version

  val commonDeps = Seq(
    akkaActor, akkaSlf4j, akkaTestkit % Test,
    sprayCan, sprayRouting, sprayTestkit % Test,
    specs2core % Test
  )
}
