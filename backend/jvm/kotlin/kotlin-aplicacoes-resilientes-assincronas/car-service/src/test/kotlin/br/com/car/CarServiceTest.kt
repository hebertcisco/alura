package br.com.car

import br.com.car.core.service.CarService
import br.com.car.domain.ports.CarRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CarServiceTest : FunSpec({
    val car = CarFixture.getCar()

    lateinit var carRepository: CarRepository
    lateinit var carService: CarService

    beforeTest {
        carRepository = mockk {
            every { listAll() } returns listOf(car)
            every { listByModel(any()) } returns listOf(car)
            every { findById(1) } returns car
            every { save(any()) } returns 0
            every { update(any(), 1) } returns 0
        }

        carService = CarService(carRepository)
    }

    test("should return a list of cars") {
        val actual = carService.list(null)

        actual shouldBe listOf(car)
    }

    test("should return a list of cars by model") {
        val actual = carService.list("Uno")

        actual shouldBe listOf(car)
    }

    test("should return a car by id") {
        val actual = carService.findById(1)

        actual shouldBe car
    }

    test("should throw a exception when not found car by id") {
        every { carRepository.findById(1) } returns null

        shouldThrow<RuntimeException> {
            carService.findById(1)
        }
    }

    test("should save a car") {
        val actual = carService.save(car)

        actual shouldBe 0
    }

    test("should update a car") {
        val actual = carService.update(car, 1)

        actual shouldBe 0
    }

    test("should throw an exception when car is not found") {
        val actual = runCatching { carService.findById(2) }

        actual.isFailure shouldBe true
    }
})