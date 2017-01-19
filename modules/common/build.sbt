name := "Common"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  "eu.bitwalker" % "UserAgentUtils" % "1.20"
)

maintainer in Linux := "Fabio Mazzone<fabio.mazzone@me.com>"