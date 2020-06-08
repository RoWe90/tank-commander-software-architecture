lazy val commonSettings = Seq(
  name := "TankCommander",
  version := "0.0.1",
  scalaVersion := "2.12.4"
)


lazy val root = (project in file("MainModule")).settings(
  commonSettings,
  libraryDependencies ++= commonDependencies,
)

lazy val player = (project in file("playerModule")).settings(
  commonSettings,
  libraryDependencies ++= commonDependencies,
)

lazy val tank = (project in file("TankModule")).settings(
  commonSettings,
  libraryDependencies ++= commonDependencies,
)

val commonDependencies = Seq(
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1",
  "com.google.inject" % "guice" % "4.2.3",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1",
  "com.typesafe.play" %% "play-json" % "2.6.6",
  "net.liftweb" %% "lift-json" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "junit" % "junit" % "4.8" % "test",
  "com.typesafe.akka" %% "akka-http" % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5"
)

// set the main class for 'sbt run'
mainClass in(Compile, run) := Some("tankcommander.TankCommander")

// set the main class for packaging the main jar
mainClass in(Compile, packageBin) := Some("tankcommander.TankCommander")




