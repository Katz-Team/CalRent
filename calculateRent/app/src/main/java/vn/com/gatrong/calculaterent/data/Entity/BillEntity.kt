package vn.com.gatrong.calculaterent.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.ElectricityBill
import vn.com.gatrong.calculaterent.model.WaterBill

@Entity
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo
    val moneyRent: Long,
    @ColumnInfo
    val preElectric: Int,
    @ColumnInfo
    val newElectric: Int,
    @ColumnInfo
    val priceElectric: Int,
    @ColumnInfo
    val preWater: Int,
    @ColumnInfo
    val newWater: Int,
    @ColumnInfo
    val priceWater: Int,
    @ColumnInfo
    val timeFrom: Long,
    @ColumnInfo
    val timeTo: Long,
) {
    constructor(bill: Bill) :
            this(id = bill.id,
                moneyRent = bill.moneyRent,
                preElectric = bill.electricityBill.preElectric,
                newElectric = bill.electricityBill.newElectric,
                priceElectric = bill.electricityBill.price,
                preWater = bill.waterBill.preWater,
                newWater = bill.waterBill.newWater,
                priceWater = bill.waterBill.price,
                timeFrom = bill.timeFrom,
                timeTo = bill.timeTo
            )

    fun toBill() : Bill {
        return Bill(id = this.id,
            moneyRent = this.moneyRent,
            electricityBill = ElectricityBill(this.preElectric,this.newElectric,this.priceElectric),
            waterBill = WaterBill(this.preWater,this.newWater,this.priceWater),
            timeFrom = this.timeFrom,
            timeTo = this.timeTo,
            arrayListOf()
        )
    }

}
