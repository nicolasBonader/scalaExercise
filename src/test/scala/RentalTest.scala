import org.scalatest.FunSuite

class RentalTest extends FunSuite {

  test("not finished rental should not produce a cost") {
    val r = new Rental(0L)

    assert(r.getCost.isEmpty)
  }

  test("cero time should be free of charge") {
    val r = new Rental(0L, 0L)

    assert(r.getCost.contains(0))
  }

  test("invalid dates should produce cero cost") {
    val r = new Rental(10000000L, 0)

    assert(r.getCost.contains(0))
  }

  test("less than an hour should be charged as an hour") {
    val r = new Rental(0L, 10L)

    assert(r.getCost.contains(Config.costPerHour))
  }

  test("three and a half hours should be charged as four hours") {
    val threeAndAHalf = 3600*1000*3 + 3600*500
    val r = new Rental(0L, threeAndAHalf)

    assert(r.getCost.contains(Config.costPerHour * 4))
  }

  test("Daily rental should charge a day if ended within 24h") {
    val r = new Rental(0L, 3600*1000*3, Daily())

    assert(r.getCost.contains(Config.costADay))
  }

  test("Daily rental should charge a day plus hourly for exceeding hours") {
    val threeAndAHalf = 3600*1000*3 + 3600*500
    val milisInADay = 3600*1000*24
    val r = new Rental(0L, milisInADay + threeAndAHalf, Daily())

    assert(r.getCost.contains(Config.costADay + Config.costPerHour * 4))
  }

  test("Weekly rental should charge a week plus hourly for exceeding hours") {
    val threeAndAHalf = 3600*1000*3 + 3600*500
    val milisInAWeek = 3600*1000*24*7
    val r = new Rental(0L, milisInAWeek + threeAndAHalf, Weekly())

    assert(r.getCost.contains(Config.costAWeek + Config.costPerHour * 4))
  }
}
