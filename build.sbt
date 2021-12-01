val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(CalibanPlugin)
  .settings(
    name := "scala-graphql-client",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.github.ghostdogpr" %% "caliban-client" % "1.3.0",
      "com.softwaremill.sttp.client3" %% "httpclient-backend" % "3.3.17",
      "com.novocode" % "junit-interface" % "0.11" % "test",
    )
  )
