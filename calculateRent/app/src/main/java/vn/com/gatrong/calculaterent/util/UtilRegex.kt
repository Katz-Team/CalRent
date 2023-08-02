package vn.com.gatrong.calculaterent.util

class UtilRegex {
    companion object {
        fun matchesFormatDate(s : String) : Boolean {
            return Regex("\\d{2}\\.\\d{2}\\.\\d{4}").matches(s)
        }
    }
}