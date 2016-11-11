name := """Service-Module-SQL"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.8"

lazy val loadEmberProject = taskKey[Unit]("Test")
loadEmberProject := {
  import sys.process._
  Seq("sh", "loadEmberProject.sh")!
}

compile in Compile <<= (compile in Compile).dependsOn(loadEmberProject)

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" %% "webjars-play" % "2.5.0-3",
  "org.webjars" % "bootstrap" % "3.3.7-1"
)
