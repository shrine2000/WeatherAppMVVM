package app.weather.service

import app.weather.model.Modal
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    val UNITS = "metric"
    val API_KEY: String? = null
    val defaultLang = "en"

    private val BASE_URL = "https://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(lat: String, lon: String): Single<Modal> {
        if (API_KEY.isNullOrBlank()) throw Exception("Add your own api key")
        return api.getData(lat,lon,  UNITS, defaultLang, API_KEY)
    }

}