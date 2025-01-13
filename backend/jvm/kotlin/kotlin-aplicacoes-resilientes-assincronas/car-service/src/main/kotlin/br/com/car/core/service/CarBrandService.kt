package br.com.car.core.service

import br.com.car.domain.model.CarBrand
import br.com.car.domain.ports.CarBrandRepository
import br.com.car.domain.ports.CarBrandService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CarBrandService(
    private val carBrandRepository: CarBrandRepository
) : CarBrandService {

    fun saveAll(carBrands: List<CarBrand>) {
        carBrands.forEach {
            println("Saving ${it.name}")
            carBrandRepository.updateOrSave(it)
        }
    }

    override fun listByName(name: String?) =
        name?.let {
            carBrandRepository.listByName(it)
        } ?: carBrandRepository.listAll()
}
