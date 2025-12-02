ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "AoC",
    version := "0.1.0",
    mainClass := Some("Main")
  )
