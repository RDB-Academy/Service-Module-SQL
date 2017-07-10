Common.appSettings(messagesFilesFrom = Seq("common", "sqlTrainerService", "userService"))

lazy val commonModule = (project in file("modules/common"))
  .enablePlugins(PlayJava, PlayEbean)

lazy val userService = (project in file("modules/userService"))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(commonModule)
  .aggregate(commonModule)

lazy val sqlTrainerService = (project in file("modules/sqlTrainerService"))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(commonModule)
  .aggregate(commonModule)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(userService, sqlTrainerService, commonModule)
  .aggregate(userService, sqlTrainerService, commonModule)

libraryDependencies ++= Common.commonDependencies
