package vn.com.gatrong.calculaterent.model

class ElectricityBill(
    val preElectric : Int,
    val newElectric : Int,
    val price : Int,
) {
    fun getKgElectric() : Int {
        return newElectric - preElectric
    }

    fun getMoney() : Int {
        return getKgElectric() * price
    }
}