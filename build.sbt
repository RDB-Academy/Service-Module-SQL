name := """Service-Module-SQL"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
playEnhancerEnabled := false

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" %% "webjars-play" % "2.5.0-3",
  "org.webjars" % "bootstrap" % "3.3.7-1"
)
