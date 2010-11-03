package khaos.web.jetty
import khaos.logging.slf4s.Logger

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ContextHandlerCollection
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.webapp.WebAppContext

/**
 * Run jetty 7 server in IDE
 * 
 * @author jarod
 */
class Jetty7DevServer extends Logger {
    val server = new Server
    val scc = new SelectChannelConnector
    scc.setPort(8080)
    server.setConnectors(Array(scc))

    val wac = new WebAppContext()
    wac.setServer(server)
    wac.setContextPath("/")
    wac.setWar("src/main/webapp")

    val contexts = new ContextHandlerCollection
    contexts.setHandlers(Array(wac))

    server.setHandler(contexts)

    def setPort(p: Int) {
        scc.setPort(p)
    }

    def setContextPath(path: String) {
        wac.setContextPath(path)
    }

    def startup() {
        try {
            info(">>> STARTING EMBEDDED JETTY SERVER ...");
            server.start();
            while (System.in.available() == 0) {
                Thread.sleep(5000)
            }

            server.stop()
            server.join()
        } catch {
            case exc: Exception => {
                error("", exc)
                System.exit(100)
            }
        }
    }
}
