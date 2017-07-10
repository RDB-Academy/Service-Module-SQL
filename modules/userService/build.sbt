Common.serviceSettings("userService", messagesFilesFrom = Seq("common", "userService"))

// Add here the specific settings for this module

libraryDependencies ++= Common.commonDependencies ++: Seq(
  "eu.bitwalker"     % "UserAgentUtils"  % "1.20"
)