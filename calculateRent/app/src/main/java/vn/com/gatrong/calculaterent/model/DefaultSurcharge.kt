package vn.com.gatrong.calculaterent.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class DefaultSurcharge(
    var id : Long = 0,
    var name: String = "Phụ phí",
    var price: Long = 0,
) {
    fun toSurcharge() : Surcharge {
        return Surcharge(0,name, price.toInt())
    }
}