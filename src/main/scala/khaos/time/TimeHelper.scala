package khaos.time

import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
object TimeHelper {

	private var _systemTime = System.currentTimeMillis
	private val systemTimeTick = System.getProperty("khaos.systime.tick", "100").toLong
	private val systemTimeTickExecutor = Executors.newSingleThreadScheduledExecutor
	
	systemTimeTickExecutor.scheduleAtFixedRate(new Runnable {
		def run {
			_systemTime = System.currentTimeMillis
		}
	}, systemTimeTick, systemTimeTick, TimeUnit.MILLISECONDS)
	
	Runtime.getRuntime.addShutdownHook(new Thread {
		override def run {
			systemTimeTickExecutor.shutdown
		}
	})
	/**
	 * Lower resolution version of System.currentTimeMillis and better performance.
	 * Default resolution is 100ms, which can be change by system property khaos.systime.tick
	 */
	def systemTime = _systemTime
}