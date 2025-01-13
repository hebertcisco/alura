package br.com.car.adapters.api

import br.com.car.domain.ports.CarBrandService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars-brands")
class CarBrandResource(
    private val carBrandService: CarBrandService
) {
    @GetMapping
    fun listByName(@RequestParam(required = false) name: String?) = carBrandService.listByName(name)
}

