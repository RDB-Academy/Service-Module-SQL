import Dependencies._
import com.typesafe.sbt.packager.archetypes.ServerLoader.Systemd
import sbt.Keys.{javacOptions, scalacOptions}

lazy val commonSettings = Seq(
  organization := "de.academy",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8",

  maintainer := "Fabio Mazzone<fabio.mazzone@me.com>",

  resolvers += Resolver.mavenLocal,

  libraryDependencies ++= Seq(
    mockito
  ),

  javacOptions := Seq("-Xlint:deprecation"),
  scalacOptions := Seq("-unchecked", "-deprecation")
)

lazy val coreModule = (project in file("modules/core"))
  .settings(commonSettings: _*)
  .settings(
    name := "core module",

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
    name := "sql-module",

    packageSummary := "RDB Academy SQL Module",

    serverLoading in Debian := Systemd,

    libraryDependencies ++= Seq(
      cache,
      javaWs
    )
  )
  .enablePlugins(PlayJava, PlayEbean, DebianPlugin)
  .dependsOn(coreModule)
  .aggregate(coreModule)

name in Debian := "rdb-academy-sql"

// debianPackageDependencies in Debian ++= Seq("nginx", "mysql-server")

daemonUser in Linux := normalizedName.value         // user which will execute the application

daemonGroup in Linux := (daemonUser in Linux).value // group which will execute the application
