name := """test-nimbus-jose-jwt"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "com.nimbusds" % "nimbus-jose-jwt" % "4.1"
libraryDependencies += "com.google.code.gson" % "gson" % "2.5"


// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"



fork in run := true