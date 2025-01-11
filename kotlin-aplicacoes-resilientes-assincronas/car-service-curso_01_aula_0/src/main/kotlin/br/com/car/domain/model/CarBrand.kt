package br.com.car.domain.model

import java.io.Serializable

data class CarBrand(
    val id: Long,
    val name: String,
    val code: Long,
) : Serializable {

}
