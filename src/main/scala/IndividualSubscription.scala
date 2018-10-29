class IndividualSubscription(initialRental: Rental) extends Subscription {
  private var rental = initialRental

  override def getCost: Int = {
    rental.getCost.getOrElse(0)
  }

  def startTime: Long = {
    rental.startValue
  }

  def hasEnded: Boolean = {
    rental.endValue.isDefined
  }

  def endTime: Long = {
    if (rental.endValue.isEmpty) {
      throw new IllegalStateException("Cannot get end time of a ongoing subscription")
    }

    rental.endValue.get
  }

  def endSubscription(time: Long): Unit = {
    rental = new Rental(rental.startValue, time, rental.`type`)
  }
}