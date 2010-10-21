// vim: set ts=4 sw=4 et:
import sbt._

class KhaosProject(info: ProjectInfo) extends DefaultProject(info) {

    override def libraryDependencies = Set(
        "org.slf4j" % "slf4j-api" % "[1.5, )" % "provided"
    ) ++ super.libraryDependencies
}


