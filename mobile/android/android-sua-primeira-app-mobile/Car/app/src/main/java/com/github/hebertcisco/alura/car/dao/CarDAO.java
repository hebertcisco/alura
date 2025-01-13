package com.github.hebertcisco.alura.car.dao;

import android.content.Context;
import android.util.Log;

import com.github.hebertcisco.alura.car.adapters.configuration.retrofit.CarApiHttpConfiguration;
import com.github.hebertcisco.alura.car.adapters.http.CarApiHttpService;
import com.github.hebertcisco.alura.car.adapters.http.CarHttp;
import com.github.hebertcisco.alura.car.model.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDAO {
    private final CarApiHttpService carApiHttpService;

    public CarDAO(Context context, CarApiHttpConfiguration configuration) {
        if (context == null) {
            throw new IllegalArgumentException("Context is required");
        }
        this.carApiHttpService = configuration.carApiHttpService();
    }

    public void save(Car car) {
        CarHttp carHttp = new CarHttp(car.getId(), car.getName(), car.getModel(), car.getYear());
        carApiHttpService.saveCar(carHttp).enqueue(new Callback<CarHttp>() {
            @Override
            public void onResponse(Call<CarHttp> call, Response<CarHttp> response) {
                if (response.isSuccessful()) {
                    Log.d("CarDAO", "Car saved successfully");
                } else {
                    Log.e("CarDAO", "Failed to save car");
                }
            }

            @Override
            public void onFailure(Call<CarHttp> call, Throwable t) {
                Log.e("CarDAO", "Error saving car", t);
            }
        });
    }

    public void all(Callback<List<CarHttp>> callback) {
        carApiHttpService.listCars().enqueue(callback);
    }

    public void delete(Long id) {
        carApiHttpService.deleteCar(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("CarDAO", "Car deleted successfully");
                } else {
                    Log.e("CarDAO", "Failed to delete car");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("CarDAO", "Error deleting car", t);
            }
        });
    }

    public void getCar(Long id, Callback<CarHttp> callback) {
        carApiHttpService.getCar(id).enqueue(callback);
    }
}