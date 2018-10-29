sealed trait RentalType
case class Hourly() extends RentalType
case class Daily() extends RentalType
case class Weekly() extends RentalType