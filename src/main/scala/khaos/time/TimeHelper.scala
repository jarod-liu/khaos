package khaos.time

import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors

object TimeHelper {
	lazy val MillisPerSecond = 1000L
	lazy val MillisPerMinute = MillisPerSecond * 60
	lazy val MillisPerHour = MillisPerMinute * 60
	
	lazy val SecondsPerMinute = 60
	lazy val SecondsPerHour = SecondsPerMinute * 60

	@volatile
	private var _systemTime = System.currentTimeMillis
	private val systemTimeTick = System.getProperty("khaos.systime.tick", "200").toLong
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
	 * Default resolution is 200ms, which can be change by system property khaos.systime.tick
	 */
	def systemTime = _systemTime
}