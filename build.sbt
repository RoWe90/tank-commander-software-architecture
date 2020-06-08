import sbt.Keys.libraryDependencies


lazy val commonSettings = Seq(
  name := "TankCommander",
  version := "0.0.1",
  scalaVersion := "2.12.4"
)


lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("tankcommander.TankCommander"),
    libraryDependencies ++= commonDependencies
  ).aggregate(
  stateModule,
  gridModule,
  playerModule,
).dependsOn(stateModule, gridModule, playerModule)


lazy val gridModule = (project in file("gridModule")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "gridModule.jar",
    libraryDependencies ++= commonDependencies

    // more settings here ...
  ).dependsOn(stateModule).aggregate(stateModule)

//lazy val gridModule = project.settings(
//  commonSettings,
//  name := "GridModule",
//  libraryDependencies ++= commonDependencies,
//).dependsOn(stateModule).aggregate(stateModule)

//// Gamestatus
//lazy val stateModule = project.settings(
//  commonSettings,
//  name := "StateModule",
//  libraryDependencies ++= commonDependencies,
//).dependsOn(playerModule).aggregate(playerModule)

lazy val stateModule = (project in file("stateModule")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "stateModule.jar",
    libraryDependencies ++= commonDependencies
    // more settings here ...
  ).dependsOn(playerModule).aggregate(playerModule)

//// Player - depends on Gamestatus
//lazy val playerModule = project.settings(
//  commonSettings,
//  name := "PlayerModule",
//  libraryDependencies ++= commonDependencies,
//)

lazy val playerModule = (project in file("playerModule")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "playerModule.jar",
    libraryDependencies ++= commonDependencies
    // more settings here ...
  )

val commonDependencies = Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.5",
  "com.typesafe.akka" %% "akka-http" % "10.1.12",
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1",
  "com.google.inject" % "guice" % "4.1.0",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
  "com.typesafe.play" %% "play-json" % "2.6.6",
  "net.liftweb" %% "lift-json" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "junit" % "junit" % "4.8" % "test",
  "com.typesafe.akka" %% "akka-http" % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5"
)

mainClass in(Compile, packageBin) := Some("tankcommander.TankCommander")



