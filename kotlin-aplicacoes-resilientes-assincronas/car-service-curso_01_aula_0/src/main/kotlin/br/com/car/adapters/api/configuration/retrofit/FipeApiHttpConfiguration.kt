package br.com.car.adapters.api.configuration.retrofit

import br.com.car.adapters.http.FipeApiHttpService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class FipeApiHttpConfiguration {
    private companion object {
        const val BASE_URL = "https://parallelum.com.br"
    }

    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    @Bean
    fun fipeApiHttpService(): FipeApiHttpService = buildRetrofit().create(FipeApiHttpService::class.java)
}