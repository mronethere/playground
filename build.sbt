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
    name := "common code"
  )

lazy val gamesolver = (project in file("gamesolver"))
  .dependsOn(common)
  .enablePlugins(PlayScala)
  .settings(commonSettings: _*)
  .settings(
    name := "the game solver",
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

lazy val tictactoe = (project in file("tictactoe"))
  .dependsOn(common)
  .settings(commonSettings: _*)
  .settings(
    name := "tic tact toe",
    version := "0.1-SNAPSHOT",
    libraryDependencies ++= sprayDeps
  )
  .settings(Revolver.settings: _*)

lazy val hm = (project in file("happiness-meter"))
  .settings(
    name := "happiness meter",
    version := "0.1-SNAPSHOT",
    libraryDependencies ++= sparkDeps
  )
