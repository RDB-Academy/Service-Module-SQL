import sbt._

/**
  * @author fabiomazzone
  * @version 1.0, 06.07.17
  */
object Dependencies {
  val h2:             ModuleID =  "com.h2database"  % "h2"              % "1.4.194"
  val userAgentUtils: ModuleID = "eu.bitwalker"     % "UserAgentUtils"  % "1.20"
  val mockito:        ModuleID = "org.mockito"      % "mockito-core"    % "2.7.17"  % Test
  val assertjCore:    ModuleID = "org.assertj"      % "assertj-core"    % "3.6.2"   % Test
  val awaitility:     ModuleID = "org.awaitility"   % "awaitility"      % "2.0.0"   % Test
}
