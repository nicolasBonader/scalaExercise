import org.scalatest.FunSuite

class IndividualSubscriptionTest extends FunSuite {

  test("subscription should get cost from rental") {
    val rental = new Rental(0L, 10L)
    val sub = new IndividualSubscription(rental)

    assert(sub.getCost == rental.getCost.get)
  }

  test("subscription should charge zero if rental has not ended") {
    val rental = new Rental(0)
    val sub = new IndividualSubscription(rental)

    assert(sub.getCost == 0)
  }

  test("subscription should return initial time of rental") {
    val rental = new Rental(123L)
    val sub = new IndividualSubscription(rental)

    assert(sub.startTime == rental.startTime)
  }

  test("subscription should indicate whether a rental has ended") {
    val rental1 = new Rental(0)
    val rental2 = new Rental(0, 12)
    val sub1 = new IndividualSubscription(rental1)
    val sub2 = new IndividualSubscription(rental2)

    assert(!sub1.hasEnded)
    assert(sub2.hasEnded)
  }

  test("subscription should end a rental properly") {
    val rental = new Rental(123L)
    val sub = new IndividualSubscription(rental)
    sub.endSubscription(12)

    assert(sub.hasEnded)
  }

  test("subscription should throw an error if asked for the end time of an ongoing rental") {
    val rental = new Rental(123L)
    val sub = new IndividualSubscription(rental)

    assertThrows[IllegalStateException] {
      sub.endTime
    }
  }

  test("subscription should report end time of an ended rental") {
    val rental = new Rental(123L, 456L)
    val sub = new IndividualSubscription(rental)

    assert(sub.endTime == rental.endTime.get)
  }
}
