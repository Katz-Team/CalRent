package vn.com.gatrong.calculaterent.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import vn.com.gatrong.calculaterent.model.Surcharge

@Entity(foreignKeys = arrayOf(ForeignKey(entity = BillEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("idBill"))))
data class SurchargeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo
    val idBill : Long,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val price : Int
) {

    constructor(surcharge: Surcharge, idBill: Long) : this(
        id = surcharge.id,
        idBill = idBill,
        name = surcharge.name,
        price = surcharge.price
    )

    fun toSurcharge(): Surcharge {
        return Surcharge(id,name, price)
    }

}