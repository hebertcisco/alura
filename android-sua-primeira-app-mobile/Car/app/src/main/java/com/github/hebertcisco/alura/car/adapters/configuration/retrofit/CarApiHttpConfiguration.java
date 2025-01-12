package com.github.hebertcisco.alura.car.adapters.configuration.retrofit;

import android.content.Context;
import android.util.Log;

import com.github.hebertcisco.alura.car.R;
import com.github.hebertcisco.alura.car.adapters.http.CarApiHttpService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarApiHttpConfiguration {
    private final String baseUrl;

    public CarApiHttpConfiguration(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context is required");
        }
        Log.d("CarApiHttpConfiguration", "Base URL: " + context.getString(R.string.api_url));
        if (context.getString(R.string.api_url).isEmpty()) {
            throw new IllegalArgumentException("Base URL is required");
        }
        this.baseUrl = context.getString(R.string.api_url);
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder().build();
    }

    private Retrofit buildRetrofit() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("Base URL is required");
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildClient())
                .build();
    }

    public CarApiHttpService carApiHttpService() {
        return buildRetrofit().create(CarApiHttpService.class);
    }
}