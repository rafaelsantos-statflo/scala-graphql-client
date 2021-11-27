val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(CalibanPlugin)
  .settings(
    name := "scala-graphql-client",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "com.github.ghostdogpr" %% "caliban-client" % "1.2.1"
    )
  )
