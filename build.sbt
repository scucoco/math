lazy val examples = (project in file(".")).
  settings(
    name := "examples",
    version := "1.0",
    scalaVersion := "2.11.7",
    scalaSource in Compile := baseDirectory.value,
    unmanagedJars in Compile += baseDirectory.value / "../deploy/mmt.jar",
    classDirectory in Compile := baseDirectory.value / "bin"
  )
