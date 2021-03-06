name := "Common"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  "eu.bitwalker" % "UserAgentUtils" % "1.20",
  "org.mockito" % "mockito-core" % "2.7.17"
)

maintainer in Linux := "Fabio Mazzone<fabio.mazzone@me.com>"