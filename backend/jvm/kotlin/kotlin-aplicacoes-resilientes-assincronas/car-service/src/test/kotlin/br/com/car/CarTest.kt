package br.com.car

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CarTest : FunSpec({
    val car = CarFixture.getCar()

    test("should return isEligible for uber when year is greater than 2008") {
        val actual = car.isEligibleToUber()

        actual.isEligible shouldBe true
    }

    test("should return isEligible for uber when year is less than 2008") {
        val newCar = CarFixture.getCar().copy(year = 2007)
        val actual = newCar.isEligibleToUber()

        actual.isEligible shouldBe false
    }

    test("should return isEligible for uber when year is equal to 2008") {
        val newCar = CarFixture.getCar().copy(year = 2008)
        val actual = newCar.isEligibleToUber()

        actual.isEligible shouldBe true
    }

    test("should return isEligible for uber when year is equal to 2009") {
        val newCar = CarFixture.getCar().copy(year = 2009)
        val actual = newCar.isEligibleToUber()

        actual.isEligible shouldBe true
    }
})