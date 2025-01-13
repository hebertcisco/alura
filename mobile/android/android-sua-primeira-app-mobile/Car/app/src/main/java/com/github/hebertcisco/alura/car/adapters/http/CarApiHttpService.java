package com.github.hebertcisco.alura.car.adapters.http;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface CarApiHttpService {
    @GET("/cars")
    Call<List<CarHttp>> listCars();

    @GET("/cars-inventory")
    Call<List<CarHttp>> listCarsByInventory();

    @POST("/cars")
    Call<CarHttp> saveCar(@Body CarHttp car);

    @PUT("/cars/{id}")
    Call<CarHttp> updateCar(@Body CarHttp car, @Path("id") Long id);

    @DELETE("/cars/{id}")
    Call<Void> deleteCar(@Path("id") Long id);

    @GET("/cars/{id}")
    Call<CarHttp> getCar(@Path("id") Long id);
}