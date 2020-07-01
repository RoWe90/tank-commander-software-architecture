lazy val commonSettings = Seq(
  name := "TankCommanderPlayer",
  version := "0.0.1",
  scalaVersion := "2.12.4"
)

lazy val MainModule = (project in file(".")).settings(
  commonSettings,
  libraryDependencies ++= commonDependencies,
  libraryDependencies ++= slickDependencies,
  libraryDependencies ++= mongoDependencies,
)

val mongoDependencies = Seq(
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
)

val slickDependencies = Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.h2database" % "h2" % "1.4.199",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
)

val commonDependencies = Seq(
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1",
  "com.google.inject" % "guice" % "4.1.0",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",
  "com.typesafe.play" %% "play-json" % "2.6.6",
  "net.liftweb" %% "lift-json" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "junit" % "junit" % "4.8" % "test",
  "com.typesafe.akka" %% "akka-http"   % "10.1.12",
  "com.typesafe.akka" %% "akka-stream" % "2.6.5"
)
