package br.com.car.domain.ports

import br.com.car.domain.model.CarBrand

interface CarBrandRepository {
    fun listAll(): List<CarBrand>
    fun listByName(name: String): List<CarBrand>
    fun save(carBrand: CarBrand): Int
    fun update(carBrand: CarBrand, id: Long): Int
    fun updateOrSave(carBrand: CarBrand)
    fun findById(id: Long): CarBrand?
    fun findByCode(code: Long): CarBrand?
    fun findByName(name: String): CarBrand?
}
