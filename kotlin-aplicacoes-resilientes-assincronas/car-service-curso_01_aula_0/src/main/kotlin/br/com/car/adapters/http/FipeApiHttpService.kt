package br.com.car.adapters.http

import org.springframework.stereotype.Service
import retrofit2.http.GET
import retrofit2.Call

@Service
interface FipeApiHttpService {
    @GET("/fipe/api/v1/carros/marcas")
    fun listCarBrands(): Call<List<CarBrandsHttp>>

    @GET("/fipe/api/v1/motos/marcas")
    fun listMotorcycleBrands(): Call<List<CarBrandsHttp>>

    @GET("/fipe/api/v1/caminhoes/marcas")
    fun listTruckBrands(): Call<List<CarBrandsHttp>>
}