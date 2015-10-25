import sbt.Keys._
import Dependencies._

lazy val commonSettings = Seq(
  organization := "playground",
  scalaVersion := "2.11.7",
  scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
)

lazy val common = (project in file("common"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDeps)
  .settings(
    name := "common settings"
  )

lazy val game = (project in file("game"))
  .dependsOn(common)
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .settings(
    name := "the game",
    version := "0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      jdbc,
      cache,
      ws,
      specs2 % Test
    ),
    resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
    routesGenerator := InjectedRoutesGenerator
  )
