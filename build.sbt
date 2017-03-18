/// packageSummary
import com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart

name := """rdb-academy-sql"""

version := "0.1-alpha"

scalaVersion := "2.11.8"

lazy val commonModule = (project in file("modules/common")).enablePlugins(PlayJava, PlayEbean)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, DebianPlugin)
  .dependsOn(commonModule)
  .aggregate(commonModule)

javacOptions := Seq("-Xlint:deprecation")
scalacOptions := Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  cache,
  javaWs,
  "org.mockito" % "mockito-core" % "2.7.17"
)

name in Debian := "rdb-academy-sql"

maintainer in Linux := "Fabio Mazzone<fabio.mazzone@me.com>"

packageSummary in Linux := "SQL.Academy"

// debianPackageDependencies in Debian ++= Seq("nginx", "mysql-server")

serverLoading in Debian := Upstart

daemonUser in Linux := normalizedName.value         // user which will execute the application

daemonGroup in Linux := (daemonUser in Linux).value // group which will execute the application
