package app.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.weather.model.Modal
import app.weather.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val weatherApiService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    val weatherData = MutableLiveData<Modal>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    fun refreshData(lat: String, lon: String) {
        getDataFromAPI(lat, lon)
    }

    private fun getDataFromAPI(lat: String, lon: String) {

        weatherLoading.value = true
        disposable.add(
            weatherApiService.getDataService(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Modal>() {

                    override fun onSuccess(t: Modal) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoading.value = false
                        Log.e(TAG, "onError: $e")
                    }

                })
        )

    }
}