package khaos.collections

import _root_.java.util.{Date, Calendar}
import _root_.java.text.SimpleDateFormat
import _root_.scala.collection.mutable.HashMap

object MapBean {
  def None = new MapBean()
  def apply(elems: (Any, Any)*) = None ++ elems
  def apply() = None
}

class MapBean extends HashMap[Any, Any] {

  private lazy val dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  private lazy val dFormat = new SimpleDateFormat("yyyy-MM-dd")
  
  protected def getAny(key: Any): Any = {
    try {
      this(key)
    } catch {
      case ex: NoSuchElementException => null
    }
  }

  def bool(key: Any): Option[Boolean] = {
    getAny(key) match {
      case v: Boolean => Some(v)
      case v: Number => Some(v!=0)
      case _ => None
    }
  }
  
  def byte(key: Any): Option[Byte] = {
    getAny(key) match {
      case v: Byte => Some(v)
      case v: Number => Some(v.byteValue)
      case v: String => try {
        Some(v.toByte)
      } catch {
        case ex: NumberFormatException => None
      }
      case _ => None
    }
  }
  
  def short(key: Any): Option[Short] = {
    getAny(key) match {
      case v: Short => Some(v)
      case v: Number => Some(v.shortValue)
      case v: String => try {
        Some(v.toShort)
      } catch {
        case ex: NumberFormatException => None
      }
      case _ => None
    }
  }
  
  def int(key: Any): Option[Int] = {
    getAny(key) match {
      case v: Int => Some(v)
      case v: Number => Some(v.intValue)
      case v: String => try {
        Some(v.toInt)
      } catch {
        case ex: NumberFormatException => None
      }
      case _ => None
    }
  }
  
  def long(key: Any): Option[Long] = {
    getAny(key) match {
      case v: Long => Some(v)
      case v: Number => Some(v.longValue)
      case v: String => try {
        Some(v.toLong)
      } catch {
        case ex: NumberFormatException => None
      }
      case _ => None
    }
  }

	def float(key:Any):Option[Float] = {
		getAny(key) match {
			case v:Float => Some(v)
			case v:Number=> Some(v.floatValue)
			case v:String => try {
				Some(v.toFloat)
			} catch {
				case ex: NumberFormatException => None
			}
			case _ => None
		}
	}
  
  def double(key: Any): Option[Double] = {
    getAny(key) match {
      case v: Double => Some(v)
      case v: Number => Some(v.doubleValue)
      case v: String => try {
        Some(v.toDouble)
      } catch {
        case ex: NumberFormatException => None
      }
      case _ => None
    }
  }
  
  def str(key: Any): Option[String] = {
    getAny(key) match {
      case v: String => Some(v)
      case null => None
      case v: Any => Some(v.toString)
    }
  }

  def date(key: Any): Option[java.util.Date] = getAny(key) match {
    case null => None
    case v: String => Some(if (v.length>10) dtFormat.parse(v) else dFormat.parse(v))
    case v: Any => Some(v.asInstanceOf[Date])
  }

  def datetimeStr(key: Any): Option[String] = {
    getAny(key) match {
      case v: String => Some(v)
      case null => None
      case v: Any => Some(dtFormat.format(v))
    }
  }
  
  def dateStr(key: Any): Option[String] = {
    getAny(key) match {
      case v: String => Some(v)
      case null => None
      case v: Any => Some(dFormat.format(v))
    }
  }
  
  /**
   * represents the number of seconds since the start of the Unix epoch: midnight UTC of January 1, 1970
   */
  def dateSecs(key: Any): Option[Long] = {
    getAny(key) match {
      case v: Date => Some(v.getTime / 1000L)
      case _ => None
    }
  }
}

