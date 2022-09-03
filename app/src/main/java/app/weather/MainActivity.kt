package app.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.weather.databinding.ActivityMainBinding
import app.weather.model.Weather
import app.weather.model.WeatherModel
import app.weather.service.GpsTracker
import app.weather.utils.*
import app.weather.viewmodel.MainViewModel
import coil.load
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.github.aachartmodel.aainfographics.aachartcreator.*
import java.sql.Timestamp
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    lateinit var locationManager: LocationManager

    private val REQUEST_LOCATION = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        Toast.makeText(
            this,
            "Select location icon to fetch weather from gps coordinates",
            Toast.LENGTH_LONG
        ).show()

        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        getLiveData()

        binding.imgLocation.setOnClickListener {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS()
            } else {
                getLocation()
            }
        }

        binding.imgCalender.setOnClickListener {
            MaterialDialog(this@MainActivity).show {
                cornerRadius(16f)
                datePicker { dialog, date ->

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getLiveData() {
        viewmodel.weatherData.observe(this) { data ->
            data?.let {
                val list = data.weatherModel
                val city = data.city?.name
                binding.tvCityName.text = city.toString()
                binding.tvTemp.text =
                    list[0].main?.temp.toString() + resources.getString(R.string.degree_symbol)
                binding.tvDescription.text = list[0].weather[0].main

                val calendar: Calendar = Calendar.getInstance()
                calendar.time = Date(System.currentTimeMillis())
                calendar.add(Calendar.DATE, 6)
                val timestamp = Timestamp((calendar.timeInMillis / 1000L))

                val temp = list.last()
                temp.dt = timestamp.time.toInt()
                list.add(temp)


                val distinct = list.distinctBy { it.dt.toString().timeStampWeekDayWords() }

                binding.tv1.text =
                    distinct[0].dt.toString().timeStampDate() + "/" + distinct[0].dt.toString()
                        .timeStampMonth()
                binding.img1.load("https://openweathermap.org/img/wn/" + distinct[0].weather[0].icon + "@2x.png")
                binding.tv1Day.text = distinct[0].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv2.text =
                    distinct[1].dt.toString().timeStampDate() + "/" + distinct[1].dt.toString()
                        .timeStampMonth()
                binding.img2.load("https://openweathermap.org/img/wn/" + distinct[1].weather[0].icon + "@2x.png")
                binding.tv2Day.text = distinct[1].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv3.text =
                    distinct[2].dt.toString().timeStampDate() + "/" + distinct[2].dt.toString()
                        .timeStampMonth()
                binding.img3.load("https://openweathermap.org/img/wn/" + distinct[2].weather[0].icon + "@2x.png")
                binding.tv3Day.text = distinct[2].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv4.text =
                    distinct[3].dt.toString().timeStampDate() + "/" + distinct[3].dt.toString()
                        .timeStampMonth()
                binding.img4.load("https://openweathermap.org/img/wn/" + distinct[3].weather[0].icon + "@2x.png")
                binding.tv4Day.text = distinct[3].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv5.text =
                    distinct[4].dt.toString().timeStampDate() + "/" + distinct[4].dt.toString()
                        .timeStampMonth()
                binding.img5.load("https://openweathermap.org/img/wn/" + distinct[4].weather[0].icon + "@2x.png")
                binding.tv5Day.text = distinct[4].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv6.text =
                    distinct[5].dt.toString().timeStampDate() + "/" + distinct[5].dt.toString()
                        .timeStampMonth()
                binding.img6.load("https://openweathermap.org/img/wn/" + distinct[5].weather[0].icon + "@2x.png")
                binding.tv6Day.text = distinct[5].dt.toString().timeStampWeekDayWords().take(3)

                binding.tv7.text =
                    distinct[6].dt.toString().timeStampDate() + "/" + distinct[0].dt.toString()
                        .timeStampMonth()
                binding.img7.load("https://openweathermap.org/img/wn/" + distinct[6].weather[0].icon + "@2x.png")
                binding.tv7Day.text = distinct[6].dt.toString().timeStampWeekDayWords().take(3)


                val aaChartView1 = binding.aaChartView1
                val aaChartModel1: AAChartModel = AAChartModel()
                    .chartType(AAChartType.Spline)
                    // .title("")
                    // .subtitle("subtitle")
                    .backgroundColor("#00000000")
                    .dataLabelsEnabled(true)
                    .markerSymbol(AAChartSymbolType.Circle)
                    .colorsTheme(arrayOf("#FFFFFF", "000000"))
                    .yAxisVisible(false)
                    .xAxisVisible(false)
                    .xAxisLabelsEnabled(false)
                    .yAxisLabelsEnabled(false)
                    .legendEnabled(false)
                    .gradientColorEnable(false)
                    .series(
                        arrayOf(
                            AASeriesElement()
                                .name("")
                                .borderWidth(0)
                                .borderColor("#00000000")
                                .fillColor("#FFFFFF")
                                .color("#FFFFFF")
                                .colorByPoint(false)
                                .negativeFillColor("#FFFFFF")
                                .fillOpacity(0f)
                                .showInLegend(false)
                                .negativeColor("#FFFFFF")
                                .data(
                                    distinct.map { it.main?.tempMax!! }.toTypedArray()
                                ),

                            )
                    )

                aaChartView1.aa_drawChartWithChartModel(aaChartModel1)

                val aaChartView2 = binding.aaChartView2
                val aaChartModel2: AAChartModel = AAChartModel()
                    .chartType(AAChartType.Spline)
                    // .title("")
                    // .subtitle("subtitle")
                    .backgroundColor("#00000000")
                    .dataLabelsEnabled(true)
                    .markerSymbol(AAChartSymbolType.Circle)
                    .colorsTheme(arrayOf("#FFFFFF", "000000"))
                    .yAxisVisible(false)
                    .xAxisVisible(false)
                    .xAxisLabelsEnabled(false)
                    .yAxisLabelsEnabled(false)
                    .legendEnabled(false)
                    .gradientColorEnable(false)
                    .series(
                        arrayOf(
                            AASeriesElement()
                                .name("")
                                .borderWidth(0)
                                .borderColor("#00000000")
                                .fillColor("#FFFFFF")
                                .color("#FFFFFF")
                                .colorByPoint(false)
                                .negativeFillColor("#FFFFFF")
                                .fillOpacity(0f)
                                .showInLegend(false)
                                .negativeColor("#FFFFFF")
                                .data(
                                    distinct.map { it.main?.tempMin!! }.toTypedArray()
                                ),

                            )
                    )
                aaChartView2.aa_drawChartWithChartModel(aaChartModel2)

                binding.tvTime1.text = list[0].dt.toString().timeStampHourMinute()
                binding.imgIcon1.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp1.text =
                    list[0].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)


                binding.tvTime2.text = list[1].dt.toString().timeStampHourMinute()
                binding.imgIcon2.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp2.text =
                    list[1].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime3.text = list[2].dt.toString().timeStampHourMinute()
                binding.imgIcon3.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp3.text =
                    list[2].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime4.text = list[3].dt.toString().timeStampHourMinute()
                binding.imgIcon4.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp4.text =
                    list[3].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime5.text = list[4].dt.toString().timeStampHourMinute()
                binding.imgIcon5.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp5.text =
                    list[4].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime6.text = list[5].dt.toString().timeStampHourMinute()
                binding.imgIcon6.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp6.text =
                    list[5].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime7.text = list[6].dt.toString().timeStampHourMinute()
                binding.imgIcon7.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp7.text =
                    list[6].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)

                binding.tvTime8.text = list[7].dt.toString().timeStampHourMinute()
                binding.imgIcon8.load("https://openweathermap.org/img/wn/" + list[0].weather[0].icon + "@2x.png")
                binding.tvTemp8.text =
                    list[7].main?.temp?.roundToInt()
                        .toString() + resources.getString(R.string.degree_symbol)


                binding.tvEastWind.text = list[0].wind?.speed.toString()
                binding.tvPressure.text = list[0].main?.pressure.toString()
                binding.tvTemperature.text = list[0].main?.temp.toString()
            }

        }

        viewmodel.weatherError.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewmodel.weatherLoading.observe(this, Observer { loading ->
            loading?.let {
                if (loading) {
                    Toast.makeText(this@MainActivity, "Loading...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun OnGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes"
        ) { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(
                "No"
            ) { dialog, which -> dialog.cancel() }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val gpsTracker = GpsTracker(this@MainActivity)
            if (gpsTracker.canGetLocation()) {
                val latitude: Double = gpsTracker.getLatitude()
                val longitude: Double = gpsTracker.getLongitude()
                viewmodel.refreshData(latitude.toString(), longitude.toString())
            } else {
                gpsTracker.showSettingsAlert()
            }
        }
    }
}