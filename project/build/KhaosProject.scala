// vim: set ts=4 sw=4 et:
import sbt._

class KhaosProject(info: ProjectInfo) extends DefaultProject(info) {
    val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
    val jbossRepo = "jboss" at "https://repository.jboss.org/nexus/content/groups/public/"

    override def libraryDependencies = Set(
        "org.slf4j" % "slf4j-api" % "[1.5, )" % "provided" withSources, 
        "org.eclipse.jetty" % "jetty-webapp" % "[7, )" % "provided", 
        "org.jboss.netty" % "netty" % "[3.2,)" % "provided" withSources
    ) ++ super.libraryDependencies
}


