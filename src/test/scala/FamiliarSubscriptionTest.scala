import org.scalatest.FunSuite
import Config._

class FamiliarSubscriptionTest extends FunSuite {
  test("subscription should report total cost with the discount") {
    val rentals = Seq(
      new Rental(0L, 123L),
      new Rental(100L, 400L, Weekly()),
      new Rental(100L, 400L, Daily())
    )
    val sub = new FamiliarSubscription(rentals)
    val expectedCost = Math.ceil((costPerHour + costADay + costAWeek) * (1 - familyDiscount))

    assert(sub.getCost() == expectedCost)
  }

  test("subscription show throw an exception if asked for cost without the minimum number of rentals") {
    val sub = new FamiliarSubscription(Seq.empty)

    assertThrows[IllegalStateException] {
      sub.getCost()
    }
  }

  test("subscription should report whether all rentals have ended") {
    val emptySub = new FamiliarSubscription(Seq.empty)
    val sub1 = new FamiliarSubscription(Seq(new Rental(0L), new Rental(0L, 123L)))
    val sub2 = new FamiliarSubscription(Seq(new Rental(0L, 123L), new Rental(123L, 456L)))

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
    val sub = new FamiliarSubscription(rentals)

    assert(rentals.length >= familyMaxRentals)
    assertThrows[IllegalStateException] {
      sub.addRental(new Rental(0))
    }
  }

  test("subscription should report new cost after new rental is added") {
    val sub = new FamiliarSubscription(Seq.empty)
    val rentals = Seq(
      new Rental(0L, 10L),
      new Rental(0L, 10L),
      new Rental(0L, 10L)
    )

    rentals.foreach(r => sub.addRental(r))

    assert(sub.getCost() == Math.ceil(3 * costPerHour * (1 - familyDiscount)))
  }
}
