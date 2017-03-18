import Dependencies._
import com.typesafe.sbt.packager.archetypes.ServerLoader.Upstart
import sbt.Keys.{javacOptions, scalacOptions}

lazy val commonSettings = Seq(
  organization := "de.academy",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8",

  libraryDependencies ++= Seq(
    mockito
  ),

  javacOptions := Seq("-Xlint:deprecation"),
  scalacOptions := Seq("-unchecked", "-deprecation")
)

lazy val coreModule = (project in file("modules/common"))
  .settings(commonSettings: _*)
  .settings(
    name := """core module""",

    libraryDependencies ++= Seq(
      userAgentUtils
    )
  )
  .enablePlugins(
    PlayEbean,
    PlayJava
  )

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := """sql-module""",

    libraryDependencies ++= Seq(
      cache,
      javaWs
    )
  )
  .enablePlugins(PlayJava, DebianPlugin)
  .dependsOn(coreModule)
  .aggregate(coreModule)

name in Debian := "rdb-academy-sql"

maintainer in Linux := "Fabio Mazzone<fabio.mazzone@me.com>"

packageSummary in Linux := "SQL.Academy"

// debianPackageDependencies in Debian ++= Seq("nginx", "mysql-server")

serverLoading in Debian := Upstart

daemonUser in Linux := normalizedName.value         // user which will execute the application

daemonGroup in Linux := (daemonUser in Linux).value // group which will execute the application
