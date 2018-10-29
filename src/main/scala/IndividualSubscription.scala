class IndividualSubscription(initialRental: Rental) extends Subscription {
  private var rental = initialRental

  override def getCost: Int = {
    rental.getCost.getOrElse(0)
  }

  def startTime: Long = {
    rental.startTime
  }

  def hasEnded: Boolean = {
    rental.endTime.isDefined
  }

  def endTime: Long = {
    if (rental.endTime.isEmpty) {
      throw new IllegalStateException("Cannot get end time of a ongoing subscription")
    }

    rental.endTime.get
  }

  def endSubscription(time: Long): Unit = {
    rental = new Rental(rental.startTime, time, rental.`type`)
  }
}