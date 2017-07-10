import sbt._
import Keys._
import play.sbt.PlayImport.{specs2, _}
import com.typesafe.config._
import Dependencies._
import com.typesafe.sbt.SbtNativePackager.autoImport.maintainer
import sbt.{Resolver, TestFrameworks, Tests}

/**
  * @author fabiomazzone
  * @version 1.0, 06.07.17
  */
object Common {
  def appName = "RDB-Academy"

  def settings (theName: String) = Seq(
    name := theName,
    organization := "de.academy",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.2",
    maintainer := "Fabio Mazzone<fabio.mazzone@me.com>",

    javacOptions := Seq("-Xlint:-serial", "-Xlint:all"),
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked", "-language:reflectiveCalls", "-language:postfixOps", "-language:implicitConversions"),
    testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),

    resolvers ++= Seq(
      "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases",
      "Atlassian Releases" at "https://maven.atlassian.com/public/",
      Resolver.mavenLocal,
      Resolver.jcenterRepo,
      Resolver.sonatypeRepo("snapshots")
    )
  )

  // Settings for the app, i.e. the root project
  def appSettings (messagesFilesFrom: Seq[String]) = settings(appName) ++: Seq(
    javaOptions += s"-Dconfig.resource=root-dev.conf",
    messagesGenerator in Compile := messagesGenerate(messagesFilesFrom, baseDirectory.value, resourceManaged.value, streams.value.log),
    resourceGenerators in Compile <+= (messagesGenerator in Compile)
  )
  // Settings for every module, i.e. for every subproject
  def moduleSettings (module: String) = settings(module) ++: Seq(
    javaOptions += s"-Dconfig.resource=$module-dev.conf",
    sharedConfFilesReplicator in Compile := sharedConfFilesReplicate(baseDirectory.value / ".." / "..", resourceManaged.value, streams.value.log),
    resourceGenerators in Compile <+= (sharedConfFilesReplicator in Compile)
  )
  // Settings for every service, i.e. for admin and web subprojects
  def serviceSettings (module: String, messagesFilesFrom: Seq[String]) = moduleSettings(module) ++: Seq(
    messagesGenerator in Compile := messagesGenerate(messagesFilesFrom, baseDirectory.value / ".." / "..", resourceManaged.value, streams.value.log),
    resourceGenerators in Compile <+= (messagesGenerator in Compile)
  )

  val commonDependencies = Seq(
    guice,
    ehcache,
    ws,
    jdbc,
    h2,
    mockito,
    assertjCore,
    awaitility,
    specs2 % Test
  )

  lazy val sharedConfFilesReplicator = taskKey[Seq[File]]("Replicate shared.*.conf files.")

  def sharedConfFilesReplicate (rootDir: File, managedDir: File, log: Logger): Seq[File] = {
    val files = ((rootDir / "conf") ** "shared.*.conf").get
    val destinationDir = managedDir / "conf"
    destinationDir.mkdirs()
    files.map { file =>
      val destinationFile = destinationDir / file.getName()
      IO.copyFile(file, destinationFile)
      file
    }
  }

  val conf = ConfigFactory.parseFile(new File("conf/shared.dev.conf")).resolve()
  val langs = scala.collection.JavaConversions.asScalaBuffer(conf.getStringList("play.i18n.langs"))

  lazy val messagesGenerator = taskKey[Seq[File]]("Generate the messages resource files.")

  def messagesGenerate (messagesFilesFrom: Seq[String], rootDir: File, managedDir: File, log: Logger): Seq[File] = {
    val destinationDir = managedDir / "conf"
    destinationDir.mkdirs()
    val files = langs.map { lang =>
      val messagesFilename = s"messages.$lang"
      val originFiles = messagesFilesFrom.map(subproject => rootDir / "modules" / subproject / "conf" / "messages" / messagesFilename)
      val destinationFile = destinationDir / messagesFilename
      IO.write(destinationFile, "## GENERATED FILE ##\n\n", append = false)
      originFiles.map { file =>
        IO.writeLines(destinationFile, lines = IO.readLines(file), append = true)
      }
      destinationFile
    }
    log.info("Generated messages files")
    files
  }
}