name := """Service-Module-SQL"""

version := "0.1"

lazy val common = (project in file("modules/common")).enablePlugins(PlayJava, PlayEbean)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .aggregate(common)


scalaVersion := "2.11.8"

lazy val loadEmberProject = taskKey[Unit]("Test")
loadEmberProject := {
  import sys.process._
  Seq("sh", "loadEmberProject.sh")!
}

lazy val dev = taskKey[Unit]("Deploy FrontEnt to this Project")

dev := {
  println("Run Activator with FrontEnd")
  loadEmberProject.value
}


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3"
)
