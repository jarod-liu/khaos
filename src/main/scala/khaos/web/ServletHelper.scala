package khaos.web

import javax.servlet.http.HttpServletRequest

trait ServletHelper {

    def remoteIp(request: HttpServletRequest) = {
        request.getHeader("X-Forwarded-For") match {
            case null => request.getRemoteAddr
            case empty: String if (empty.isEmpty) => request.getRemoteAddr
            case ip => ip
        }
    }
}