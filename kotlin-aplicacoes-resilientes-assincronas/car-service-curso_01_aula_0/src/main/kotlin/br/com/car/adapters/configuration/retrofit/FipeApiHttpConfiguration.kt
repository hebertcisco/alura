package br.com.car.adapters.configuration.retrofit

import br.com.car.adapters.configuration.circuitbreaker.CircuitBreakerConfiguration
import br.com.car.adapters.http.FipeApiHttpService
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class FipeApiHttpConfiguration(
    private val circuitBreakerConfiguration: CircuitBreakerConfiguration
) {
    private companion object {
        const val BASE_URL = "https://parallelum.com.br"
    }

    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreakerConfiguration.getCircuitBreaker()))
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    @Bean
    fun fipeApiHttpService(): FipeApiHttpService = buildRetrofit().create(FipeApiHttpService::class.java)
}