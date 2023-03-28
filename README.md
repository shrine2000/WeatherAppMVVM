# Weather App

This is an Android app that retrieves weather information from the OpenWeatherMap API. The app demonstrates concepts of MVVM, RxJava, and Kotlin.

## Screenshots
![enter image description here](https://raw.githubusercontent.com/shrine2000/WeatherAppMVVM/master/other/demo.jpeg)
 

## External Dependencies

This app uses the following external dependencies:

-   `com.google.android.material:material:1.6.1`: Material Design components for Android.
-   `androidx.core:core-ktx:1.7.0`: Kotlin extensions for the Android platform.
-   `androidx.appcompat:appcompat:1.5.0`: Android support library for backwards compatibility.
-   `androidx.constraintlayout:constraintlayout:2.1.4`: Layout manager for Android that helps create complex layouts.
-   `com.google.code.gson:gson:2.9.0`: Java library that can be used to convert Java Objects into their JSON representation.
-   `com.squareup.retrofit2:retrofit:2.9.0`: A type-safe HTTP client for Android and Java.
-   `com.squareup.retrofit2:converter-gson:2.9.0`: Converter for Retrofit that uses Gson to convert JSON responses to Java objects.
-   `com.squareup.retrofit2:adapter-rxjava2:2.9.0`: Adapter for Retrofit that allows it to use RxJava 2 for asynchronous requests.
-   `io.reactivex.rxjava2:rxjava:2.2.21`: Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java Virtual Machine (JVM).
-   `io.reactivex.rxjava2:rxandroid:2.1.1`: Reactive Extensions for Android – a library for composing asynchronous and event-based programs using observable sequences for Android.
-   `com.afollestad.material-dialogs:datetime:3.3.0`: A library that provides a simple way to display a date/time picker in Android.

## ViewModel

This app uses a `MainViewModel` to handle the data for the main activity. The `MainViewModel` subscribes to the `WeatherAPIService` to get weather data from the OpenWeatherMap API using RxJava for asynchronous requests. The `MainViewModel` has three MutableLiveData fields:

-   `weatherData`: contains the weather data received from the API
-   `weatherError`: contains a Boolean indicating whether there was an error retrieving the weather data
-   `weatherLoading`: contains a Boolean indicating whether the app is currently loading weather data

## Service

The `WeatherAPIService` is responsible for making requests to the OpenWeatherMap API using Retrofit. The `getDataService()` function takes in the latitude and longitude of the location to get the weather data for, and returns a Single object that emits the weather data when it is received from the API.

## License

This project is licensed under the MIT License. 
