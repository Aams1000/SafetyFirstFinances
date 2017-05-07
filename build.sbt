name := """finances"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
    .aggregate(offlinejobs)
//    .dependsOn(offlinejobs)
    .enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.postgresql" % "postgresql" % "9.4.1212.jre7"
)

lazy val offlinejobs = project


