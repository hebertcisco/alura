package br.com.car.core.cron

import br.com.car.adapters.http.CarBrandsHttp
import br.com.car.adapters.http.FipeApiHttpService
import br.com.car.core.service.CarBrandService

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlinx.coroutines.*

@Service
class CarBrandFetcherCron(
    private val fipeApiHttpService: FipeApiHttpService,
    val carBrandService: CarBrandService
) {
    @Scheduled(cron = "0 0 0 * * *")
    fun execute(){
        runBlocking {
            val carBrands = async { listCarBrands() }
            val motorcycleBrands = async { listMotorcycleBrands() }
            val truckBrands = async { listTruckBrands() }

            println("Saving car brands")
            carBrandService.saveAll(carBrands.await().map { it.toCarBrand() }).also {
                println("Car brands saved")
            }

            println("Saving motorcycle brands")
            carBrandService.saveAll(motorcycleBrands.await().map { it.toCarBrand() }).also {
                println("Motorcycle brands saved")
            }

            println("Saving truck brands")
            carBrandService.saveAll(truckBrands.await().map { it.toCarBrand() }).also {
                println("Truck brands saved")
            }
        }
    }

    private suspend fun listCarBrands(): List<CarBrandsHttp> {
        println("Fetching car brands")
        val carBrands = fipeApiHttpService.listCarBrands().execute().also {
            if (!it.isSuccessful) {
                println("Error fetching car brands")
                return emptyList()
            }
        }.body()
        println("Car brands fetched")
        return carBrands ?: emptyList()
    }

    private suspend fun listMotorcycleBrands(): List<CarBrandsHttp> {
        println("Fetching motorcycle brands")
        val carBrands = fipeApiHttpService.listMotorcycleBrands().execute().also {
            if (!it.isSuccessful) {
                println("Error fetching motorcycle brands")
                return emptyList()
            }
        }.body()
        println("Motorcycle brands fetched")
        return carBrands ?: emptyList()
    }

    private suspend fun listTruckBrands(): List<CarBrandsHttp> {
        println("Fetching truck brands")
        val carBrands = fipeApiHttpService.listTruckBrands().execute().also {
            if (!it.isSuccessful) {
                println("Error fetching truck brands")
                return emptyList()
            }
        }.body()
        println("Truck brands fetched")
        return carBrands ?: emptyList()
    }
}