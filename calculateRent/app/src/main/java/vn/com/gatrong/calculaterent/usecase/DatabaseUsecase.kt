package vn.com.gatrong.calculaterent.usecase

import vn.com.gatrong.calculaterent.data.repository.RepositoryImpl
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.ElectricityBill
import vn.com.gatrong.calculaterent.model.WaterBill

class DatabaseUsecase {

    private lateinit var newBill : Bill

    suspend fun insertBill(kgElect: Int, kgWater: Int, time: Long ) {
        val default = RepositoryImpl.getInstance().getDefaultSetting()

        val oldBill = RepositoryImpl.getInstance().getBillLast()

        newBill = Bill(moneyRent = default.rentHouse,
            electricityBill = ElectricityBill(oldBill.electricityBill.newElectric, kgElect,default.rentElect.toInt()),
            waterBill = WaterBill(oldBill.waterBill.newWater,kgWater,default.rentWater.toInt()),
            timeFrom = oldBill.timeTo,
            timeTo = time,
            surcharges = default.toSurcharges())

        RepositoryImpl.getInstance().insertBill(newBill)
    }

    fun getNewBill(): Bill {
        return newBill
    }


}