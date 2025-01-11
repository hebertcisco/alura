package br.com.car.domain.ports

import br.com.car.domain.model.CarBrand

interface CarBrandService {
    fun listByName(name: String?): List<CarBrand>
}
