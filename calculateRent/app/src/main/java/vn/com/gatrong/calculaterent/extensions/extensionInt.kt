package vn.com.gatrong.calculaterent.extensions

import java.text.DecimalFormat

fun Int.formatToMoney() : String {
    return this.toString().formatToMoney()
}