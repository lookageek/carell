// ThisBuild refers to the current module build which is running
ThisBuild / scalaVersion := "3.2.2"
ThisBuild / versionScheme := Some("early-semver")
// Skip the publishing to sonatype for now (this is from publish plugin we have included)
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

// have a common set of settings which can be applied to all modules
val commonSettings = Seq(
  // avoiding the fatal warnings to stop the noise
  scalacOptions -= "-Xfatal-warnings",
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "3.4.8",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.7",
  ),
)

lazy val shared = project.settings(
  commonSettings,
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.0.1",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.0.1",
  ),
)

lazy val server = project
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
        "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "1.0.1",
        "org.http4s" %% "http4s-dsl" % "0.23.3",
        "org.http4s" %% "http4s-ember-server" % "0.23.3",
        "org.slf4j" % "slf4j-api" % "2.0.7",
        "org.typelevel" %% "log4cats-slf4j" % "2.1.1",
    ),
  )
  .dependsOn(shared)

lazy val client = project.settings(commonSettings).dependsOn(shared)

lazy val root = project
  .in(file("."))
  .settings(
    publish := {},
    publish / skip := true,
  )
  .aggregate(
    server,
    client,
    shared,
  ) // <- aggregate allows us to run the tests and compilation for all modules from root
