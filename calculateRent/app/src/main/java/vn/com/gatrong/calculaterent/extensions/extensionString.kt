package vn.com.gatrong.calculaterent.extensions

import java.text.DecimalFormat
import java.util.Calendar

fun String.checkFormatDate() : Boolean {
    val regex = Regex("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$")
    if(regex.containsMatchIn(this)) {
        return true
    }
    return false
}

fun String.toLongPatternDDMMYYYY() : Long {
    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR,this.split(".").get(2).toInt())
    cal.set(Calendar.MONTH,this.split(".").get(1).toInt() - 1)
    cal.set(Calendar.DAY_OF_MONTH,this.split(".").get(0).toInt())
    return cal.timeInMillis
}

fun String.formatToMoney() : String {
    if (this.isEmpty())
        return this
    val formatter = DecimalFormat("#,###")
    return formatter.format(this.toLong()).replace(",",".")
}

fun String.formatToMoneyString() : String {
    return this.replace(".","")
}