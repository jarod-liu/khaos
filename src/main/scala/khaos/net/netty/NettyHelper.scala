package khaos.net.netty

import org.jboss.netty.channel.Channel
import java.net.InetSocketAddress

object NettyHelper {

	def remoteIp(ch: Channel) = {
		ch.getRemoteAddress.asInstanceOf[InetSocketAddress].getAddress.getHostAddress
	}
}