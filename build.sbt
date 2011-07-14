name := "khaos"

version := "0.0.8"

organization := "khaos"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
	"org.slf4j" % "slf4j-api" % "[1.5, )" % "provided",
	"org.eclipse.jetty" % "jetty-webapp" % "[7, 8)" % "provided", 
	"org.jboss.netty" % "netty" % "[3.2,)" % "provided", 
	"joda-time" % "joda-time" % "[1.6, )" % "provided"
)

