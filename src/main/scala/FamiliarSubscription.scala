import Config._

class FamiliarSubscription(initialRentals: Seq[Rental]) extends Subscription {
  private var rentals: Seq[Rental] = initialRentals

  override def getCost(): Int = {
    if (familyMinRentals > rentals.length) {
      throw new IllegalStateException("Familiy subscription does not have the minimum number of rentals")
    }

    val totalWithoutDiscount = rentals.map(_.getCost.getOrElse(0)).sum
    Math.ceil(totalWithoutDiscount * familyDiscount).toInt
  }

  def addRental(rental: Rental): Unit = {
    if (rentals.length < familyMaxRentals) {
      rentals = Seq(rental) ++ rentals
    } else {
      throw new IllegalStateException("Family subscription already has the maximum number of rentals")
    }
  }

  def haveRentalsEnded: Boolean = {
    rentals.map(_.endValue.isDefined).reduce(_&&_)
  }
}