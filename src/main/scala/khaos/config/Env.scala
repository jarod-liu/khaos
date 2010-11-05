package khaos.env

import java.io.InputStream
import java.util.InvalidPropertiesFormatException
import java.io.File
import java.net.InetAddress
import khaos.collections.MapBean
import khaos.logging.slf4s.Logger
import scala.io.Source

object Env extends Logger {
    object RunModes extends Enumeration {
        val Development = Value(0, "Development")
        val Test = Value(1, "Test")
        val Production = Value(2, "Production")
    }

    import RunModes._

    lazy val mode = {
        System.getProperty("run.mode") match {
            case "Development" => Development
            case "Test" => Test
            case "Production" => Production
            case _ => Development
        }
    }

    lazy val isProductionMode = mode == Production
    lazy val isDevlopmentMode = mode == Development
    lazy val isTestMode = mode == Test

    lazy val modeName = mode match {
        case Test => "test"
        case Production => "production"
        case _ => ""
    }
    private lazy val _modeName = dotLen(modeName)

    private def dotLen(in: String): String = in match {
        case null | "" => in
        case x => x + "."
    }

    lazy val userName = System.getProperty("user.name")
    private lazy val _userName = dotLen(userName)

    lazy val hostName = InetAddress.getLocalHost.getHostName
    private lazy val _hostName = dotLen(hostName)

    private val Ext = "conf"

    /**
     * The list of paths to search for property file resources.
     */
    lazy val toTry: List[() => String] = List(
        () => "/" + _modeName + _userName + _hostName,
        () => "/" + _modeName + _userName,
        () => "/" + _modeName + _hostName,
        () => "/" + _modeName + "default.")

    lazy val props = new MapBean() {
        var source: InputStream = null
        for (f <- toTry) {
            if (source == null) {
                val file = f() + Ext
                source = getClass.getResourceAsStream(file)
                if (source != null)
                    info("Loading configuration from classpath:" + file)
                else
                    debug("Attempted loading configuration file classpath:" + file)
            }
        }
        if (source != null) {
            for (
                line <- Source.fromInputStream(source).getLines if (!line.isEmpty && !line.startsWith("#"))
            ) {
                val tokens = line.split("=", 2)
                if (tokens.length != 2)
                    warn("Ignored invalid line: " + line)
                else
                    put(tokens(0), tokens(1))
            }
        } else
            warn("No configuration file found in classpath!")
    }
}
