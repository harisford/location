name := "location"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.mongodb" % "casbah_2.11" % "2.7.4",
  "com.novus" % "salat_2.11" % "1.9.9",
 // "org.reactivemongo" %% "reactivemongo" % "0.10.5.0.akka23",
 // "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.3",
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.typesafe.akka" % "akka-http-xml-experimental_2.11" % "2.0.5",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-M2",
  "net.codingwell" %% "scala-guice" % "4.0.1",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.3",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.3",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.3",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-typed-experimental" % "2.4.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.0" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.3" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.specs2" %% "specs2-mock" % "3.6.5" % "test",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "1.48.0" % "test",
  "org.mongodb" %% "casbah" % "2.8.1" % "test",
  "com.github.simplyscala" %% "scalatest-embedmongo" % "0.2.2" % "test"
)

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"

mainClass in (Compile, run) := Some("Boot")
    