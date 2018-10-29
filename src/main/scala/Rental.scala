trait RentalType
case class Hourly() extends RentalType
case class Daily() extends RentalType
case class Weekly() extends RentalType

object Config {
  val costPerHour = 5
  val costADay = 20
  val costAWeek = 60
}

class Rental(val startValue: Long, val endValue: Option[Long] = None, val `type`: RentalType = Hourly()) {
  import Config._
  private val milisInAnHour = 3600000
  private val milisInADay = milisInAnHour * 24
  private val milisInAWeek = milisInADay * 7

  def getCost(): Option[Int] = {
    endValue map { someEndValue =>
      val timeDiff = someEndValue - startValue
      `type` match {
        case Hourly() => costPerHour * getHours(timeDiff)
        case Daily() => costADay + costPerHour * getHours(Math.max(timeDiff - milisInADay, 0))
        case Weekly() => costAWeek + costPerHour * getHours(Math.max(timeDiff - milisInAWeek, 0))
      }
    }
  }

  private def getHours(deltaMilis: Long): Int = {
    Math.ceil(deltaMilis / milisInAnHour).toInt
  }
}

object App extends App {
  val r = new Rental(0, Some(0))
  println("Hola")
  println(r.startValue)
}
