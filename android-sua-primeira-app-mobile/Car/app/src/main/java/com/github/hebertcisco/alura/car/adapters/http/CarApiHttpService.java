package com.github.hebertcisco.alura.car.adapters.http;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

public interface CarApiHttpService {
    @GET("/cars")
    Call<List<CarHttp>> listCars();

    @GET("/cars-inventory")
    Call<List<CarHttp>> listCarsByInventory();

    @POST("/cars")
    Call<CarHttp> saveCar(CarHttp car);

    @PUT("/cars/{id}")
    Call<CarHttp> updateCar(CarHttp car, Long id);

    @DELETE("/cars/{id}")
    Call<Void> deleteCar(Long id);

    @GET("/cars/{id}")
    Call<CarHttp> getCar(Long id);
}