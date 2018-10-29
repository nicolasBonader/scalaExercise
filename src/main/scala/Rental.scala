import Config._

class Rental(val startTime: Long, val endTime: Option[Long] = None, val `type`: RentalType = Hourly()) {
  private val milisInAnHour = 3600000
  private val milisInADay = milisInAnHour * 24
  private val milisInAWeek = milisInADay * 7

  def this(startValue: Long, endValue: Long, `type`: RentalType) = this(startValue, Some(endValue), `type`)

  def this(startValue: Long, endValue: Long) = this(startValue, Some(endValue))

  def this(startValue: Long, `type`: RentalType) = this(startValue, None, `type`)

  def this(startValue: Long) = this(startValue, Hourly())

  def getCost: Option[Int] = {
    endTime map { someEndTime =>
      val timeDiff = someEndTime - startTime
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