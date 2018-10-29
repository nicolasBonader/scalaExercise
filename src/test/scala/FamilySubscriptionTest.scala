import org.scalatest.FunSuite

class FamilySubscriptionTest extends FunSuite {

  implicit val config: Config = DefaultConfig

  test("subscription should report total cost with the discount") {
    val rentals = Seq(
      new Rental(0L, 123L),
      new Rental(100L, 400L, Weekly()),
      new Rental(100L, 400L, Daily())
    )
    val sub = new FamilySubscription(rentals)
    val expectedCost = Math.ceil((config.costPerHour + config.costADay + config.costAWeek) * (1 - config.familyDiscount))

    assert(sub.getCost() == expectedCost)
  }

  test("subscription show throw an exception if asked for cost without the minimum number of rentals") {
    val sub = new FamilySubscription(Seq.empty)

    assertThrows[IllegalStateException] {
      sub.getCost()
    }
  }

  test("subscription should report whether all rentals have ended") {
    val emptySub = new FamilySubscription(Seq.empty)
    val sub1 = new FamilySubscription(Seq(new Rental(0L), new Rental(0L, 123L)))
    val sub2 = new FamilySubscription(Seq(new Rental(0L, 123L), new Rental(123L, 456L)))

    assert(emptySub.haveRentalsEnded)
    assert(!sub1.haveRentalsEnded)
    assert(sub2.haveRentalsEnded)
  }

  test("subscription should throw an exception if asked to add more rentals than allowed") {
    val rentals = Seq(
      new Rental(0),
      new Rental(0),
      new Rental(0),
      new Rental(0),
      new Rental(0)
    )
    val sub = new FamilySubscription(rentals)

    assert(rentals.length >= config.familyMaxRentals)
    assertThrows[IllegalStateException] {
      sub.addRental(new Rental(0))
    }
  }

  test("subscription should report new cost after new rental is added") {
    val sub = new FamilySubscription(Seq.empty)
    val rentals = Seq(
      new Rental(0L, 10L),
      new Rental(0L, 10L),
      new Rental(0L, 10L)
    )

    rentals.foreach(r => sub.addRental(r))

    assert(sub.getCost() == Math.ceil(3 * config.costPerHour * (1 - config.familyDiscount)))
  }
}
