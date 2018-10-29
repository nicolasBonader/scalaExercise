trait Config {
  val costPerHour: Int
  val costADay: Int
  val costAWeek: Int
  val familyDiscount: Double
  val familyMinRentals: Int
  val familyMaxRentals: Int
}

object DefaultConfig extends Config {
  val costPerHour = 5
  val costADay = 20
  val costAWeek = 60
  val familyDiscount = 0.3
  val familyMinRentals = 3
  val familyMaxRentals = 5
}
