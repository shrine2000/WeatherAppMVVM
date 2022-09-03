package app.weather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun String.timeStampDate(): String {

    val mydate = Calendar.getInstance()
    mydate.timeInMillis = this.toLong() * 1000

    return mydate[Calendar.DAY_OF_MONTH].toString()
}


@SuppressLint("SimpleDateFormat")
fun String.timeStampMonth(): String {
    val mydate = Calendar.getInstance()
    mydate.timeInMillis = this.toLong() * 1000

    return mydate[Calendar.MONTH].toString()
}


fun String.timeStampWeekDayWords(): String {
    val sdf = SimpleDateFormat("EEEE")
    val dateFormat = Date(this.toLong() * 1000)
    return sdf.format(dateFormat)
}



fun String.timeStampHourMinute(): String {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this.toLong() * 1000L
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(calendar.time)

}