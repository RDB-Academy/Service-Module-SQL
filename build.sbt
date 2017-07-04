import Dependencies._
import com.typesafe.sbt.packager.archetypes.systemloader.SystemdPlugin
import sbt.Keys.{javacOptions, scalacOptions}

lazy val commonSettings = Seq(
  organization := "de.academy",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.2",

  maintainer := "Fabio Mazzone<fabio.mazzone@me.com>",

  resolvers += Resolver.mavenLocal,

  libraryDependencies ++= Seq(
    guice,
    mockito,
    "com.h2database" % "h2" % "1.4.193"
  ),

  javacOptions := Seq("-Xlint:all"),
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

    packageSummary := "RDB Academy SQL Module"

    // serverLoading in Debian := Systemd,

    //libraryDependencies ++= Seq(
    //  ehcache
    //  javaWs
    //)
  )
  .enablePlugins(PlayJava, PlayEbean, DebianPlugin, SystemdPlugin)
  .dependsOn(coreModule)
  .aggregate(coreModule)

name in Debian := "rdb-academy-sql"

debianPackageDependencies in Debian ++= Seq("nginx", "mysql-server")

daemonUser in Linux := normalizedName.value         // user which will execute the application

daemonGroup in Linux := (daemonUser in Linux).value // group which will execute the application
