
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
        "org.typelevel" %% "munit-cats-effect-3" % "1.0.7"
    )
)

lazy val shared = project.settings(commonSettings)

lazy val server = project.settings(commonSettings).dependsOn(shared)
lazy val client = project.settings(commonSettings).dependsOn(shared)

lazy val root = project.in(file("."))
    .settings(
        publish := {},
        publish / skip := true
    )
    .aggregate(server, client, shared)  // <- aggregate allows us to run the tests and compilation for all modules from root