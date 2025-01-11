package br.com.car.adapters.http

import br.com.car.domain.model.CarBrand
import java.io.Serializable

data class CarBrandsHttp(
    val codigo: String,
    val nome: String,
): Serializable {
    fun toCarBrand() = CarBrand(
        id = codigo.toLong(),
        name = nome,
        code = codigo.toLong()
    )
}
