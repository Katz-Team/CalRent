package vn.com.gatrong.calculaterent.extensions

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Long.toDateString(pattern : String = "dd/MM/yyyy") : String {
    val date = Date(this)
    val format = SimpleDateFormat(pattern)
    return format.format(date)
}

fun Long.formatToMoney() : String {
    return this.toString().formatToMoney()
}