name := "mockito-scala"

version := "0.1"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.mockito" %% "mockito-scala-scalatest" % "1.16.42" % "test"
)