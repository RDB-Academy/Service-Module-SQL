/// packageSummary
import com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart

name in Debian := "sql-academy"
name := """Service-Module-SQL"""

version := "0.1-alpha"

lazy val common = (project in file("modules/common")).enablePlugins(PlayJava, PlayEbean)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, JDebPackaging)
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
  cache,
  javaWs,
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3"
)



maintainer in Linux := "Fabio Mazzone<fabio.mazzone@me.com>"

packageSummary in Linux := "SQL.Academy"

debianPackageDependencies in Debian ++= Seq("nginx", "mysql-server")

serverLoading in Debian := Upstart

daemonUser in Linux := normalizedName.value         // user which will execute the application

daemonGroup in Linux := (daemonUser in Linux).value // group which will execute the application
