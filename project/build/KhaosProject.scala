// vim: set ts=4 sw=4 et:
import sbt._

class KhaosProject(info: ProjectInfo) extends DefaultProject(info) {

    override def libraryDependencies = Set(
        "org.slf4j" % "slf4j-api" % "[1.5, )" % "provided", 
        "org.eclipse.jetty" % "jetty-webapp" % "7.2.0.v20101020" % "provided"
    ) ++ super.libraryDependencies
}


