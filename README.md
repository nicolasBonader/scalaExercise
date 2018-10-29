# Bike Rental Company

This is the main skeleton of an application of a bike rental company. People can hire the service and will be changed as follows:

1. Rental by hour, charging $5 per hour
2. Rental by day, charging $20 a day
3. Rental by week, changing $60 a week
4. Family Rental, is a promotion that can include from 3 to 5 Rentals (of any type) with a discount of 30% of the total price

To model this problem there three main classes: `Rental`, `IndividualSubscription` and `FamilySubscription`

`Rental` class models the three types of rental (per hour, by day or by week) as well as the logic needed to charge accordingly.

`IndividualSubscription` and `FamilySubscription` classes include the logic needed to manage rentals. The former manages a single rental, whereas the later manages a set of rentals. This classes also are responsible of apply a specific configuration to those rentals, as defined in the trait `Config`.

The exists a `DefaultConfig` which implements `Config` and includes the specified parameters by the company at a given time, such as how much it charges for an hourly rental or how many rentals should a family subscription have. This `Config` can be replaced easily in order to test the classes with a custom set of parameters (see https://en.wikipedia.org/wiki/Dependency_injection)

## How to run the tests

Assuming you have sbt and scala 2.12 installed, this three steps should be enough to run the tests


```
git clone https://github.com/nicolasBonader/scalaExercise.git
cd scalaExercise
sbt test
```


