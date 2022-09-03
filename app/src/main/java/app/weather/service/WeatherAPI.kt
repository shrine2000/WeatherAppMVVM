package app.weather.service

import app.weather.model.Modal
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/forecast?lat=51.5&lon=0.12&appid=API_KEY
    @GET("data/2.5/forecast")
    fun getData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        //  @Query("cnt") cnt: Int = 7,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appId: String
    ): Single<Modal>
}