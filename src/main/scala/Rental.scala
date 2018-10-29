import Config._

sealed trait RentalType
case class Hourly() extends RentalType
case class Daily() extends RentalType
case class Weekly() extends RentalType

class Rental(val startValue: Long, val endValue: Option[Long] = None, val `type`: RentalType = Hourly()) {
  private val milisInAnHour = 3600000
  private val milisInADay = milisInAnHour * 24
  private val milisInAWeek = milisInADay * 7

  def this(startValue: Long, endValue: Long, `type`: RentalType) = this(startValue, Some(endValue), `type`)

  def this(startValue: Long, endValue: Long) = this(startValue, Some(endValue))

  def this(startValue: Long, `type`: RentalType) = this(startValue, None, `type`)

  def this(startValue: Long) = this(startValue, Hourly())

  def getCost: Option[Int] = {
    endValue map { someEndValue =>
      val timeDiff = someEndValue - startValue
      `type` match {
        case Hourly() => costPerHour * getHours(timeDiff)
        case Daily() => costADay + costPerHour * getHours(timeDiff - milisInADay)
        case Weekly() => costAWeek + costPerHour * getHours(timeDiff - milisInAWeek)
      }
    }
  }

  private def getHours(deltaMilis: Long): Int = {
    Math.ceil(Math.max(deltaMilis, 0) / milisInAnHour.toDouble).toInt
  }
}