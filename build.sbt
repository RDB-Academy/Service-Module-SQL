name := """Service-Module-SQL"""

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.8"

lazy val loadEmberProject = taskKey[Unit]("Test")
loadEmberProject := {
  import sys.process._
  Seq("sh", "loadEmberProject.sh")!
}

run in Compile <<= (run in Compile).dependsOn(loadEmberProject)

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3",
  "eu.bitwalker" % "UserAgentUtils" % "1.20"
)
