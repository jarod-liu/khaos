package khaos {
    package logging {
        package slf4s {

            import _root_.org.slf4j._

            object Logger {
                def loggerNameFor(cls: Class[_]) = {
                    val className = cls.getName
                    if (className endsWith "$")
                        className.substring(0, className.length - 1)
                    else
                        className
                }
            }

            /**
             * Logger is a thin wrapper on top of an SLF4J Logger
             *
             * The main purpose is to utilize Scala features for logging
             * 
             * Note that the dynamic type of "this" is used when this trait is
             * mixed in. 
             * 
             * This may not always be what you want. If you need the static type, you have to declare your
             * own Logger:
             * 
             * class MyClass {
             *   val logger = Logger(classOf[MyClass])
             * }
             * 
             */
            trait Logger {
                private lazy val logger: org.slf4j.Logger = createLogger
                
                protected def createLogger = LoggerFactory.getLogger(Logger.loggerNameFor(this.getClass))

                def assertLog(assertion: Boolean, msg: => String) = if (assertion) info(msg)

                /**
                 * Log the value of v with trace and return v. Useful for tracing values in expressions
                 */
                def trace[T](msg: String, v: T): T = {
                    logger.trace(msg + ": " + v.toString)
                    v
                }

                def trace(msg: => AnyRef) = if (logger.isTraceEnabled) logger.trace(String.valueOf(msg))
                def trace(msg: => AnyRef, t: Throwable) = if (logger.isTraceEnabled) logger.trace(String.valueOf(msg), t)
                def trace(msg: => AnyRef, marker: Marker) = if (logger.isTraceEnabled) logger.trace(marker, String.valueOf(msg))
                def trace(msg: => AnyRef, t: Throwable, marker: => Marker) = if (logger.isTraceEnabled) logger.trace(marker, String.valueOf(msg), t)
                def isTraceEnabled = logger.isTraceEnabled

                def debug(msg: => AnyRef) = if (logger.isDebugEnabled) logger.debug(String.valueOf(msg))
                def debug(msg: => AnyRef, t: Throwable) = if (logger.isDebugEnabled) logger.debug(String.valueOf(msg), t)
                def debug(msg: => AnyRef, marker: Marker) = if (logger.isDebugEnabled) logger.debug(marker, String.valueOf(msg))
                def debug(msg: => AnyRef, t: Throwable, marker: Marker) = if (logger.isDebugEnabled) logger.debug(marker, String.valueOf(msg), t)
                def isDebugEnabled = logger.isDebugEnabled

                def info(msg: => AnyRef) = if (logger.isInfoEnabled) logger.info(String.valueOf(msg))
                def info(msg: => AnyRef, t: => Throwable) = if (logger.isInfoEnabled) logger.info(String.valueOf(msg), t)
                def info(msg: => AnyRef, marker: Marker) = if (logger.isInfoEnabled) logger.info(marker, String.valueOf(msg))
                def info(msg: => AnyRef, t: Throwable, marker: Marker) = if (logger.isInfoEnabled) logger.info(marker, String.valueOf(msg), t)
                def isInfoEnabled = logger.isInfoEnabled

                def warn(msg: => AnyRef) = if (logger.isWarnEnabled) logger.warn(String.valueOf(msg))
                def warn(msg: => AnyRef, t: Throwable) = if (logger.isWarnEnabled) logger.warn(String.valueOf(msg), t)
                def warn(msg: => AnyRef, marker: Marker) = if (logger.isWarnEnabled) logger.warn(marker, String.valueOf(msg))
                def warn(msg: => AnyRef, t: Throwable, marker: Marker) = if (logger.isWarnEnabled) logger.warn(marker, String.valueOf(msg), t)
                def isWarnEnabled = logger.isWarnEnabled

                def error(msg: => AnyRef) = if (logger.isErrorEnabled) logger.error(String.valueOf(msg))
                def error(msg: => AnyRef, t: Throwable) = if (logger.isErrorEnabled) logger.error(String.valueOf(msg), t)
                def error(msg: => AnyRef, marker: Marker) = if (logger.isErrorEnabled) logger.error(marker, String.valueOf(msg))
                def error(msg: => AnyRef, t: Throwable, marker: Marker) = if (logger.isErrorEnabled) logger.error(marker, String.valueOf(msg), t)
                def isErrorEnabled = logger.isErrorEnabled

            }

            class WrappedLogger(l: org.slf4j.Logger) extends Logger {
                override protected def createLogger = l
            }

            trait Loggable {
                protected lazy val logger = new WrappedLogger(LoggerFactory.getLogger(Logger.loggerNameFor(this.getClass)))
            }
        }
    }
}

