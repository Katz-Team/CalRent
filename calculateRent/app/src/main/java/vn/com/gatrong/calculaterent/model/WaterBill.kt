package vn.com.gatrong.calculaterent.model

class WaterBill(
    val preWater : Int,
    val newWater : Int,
    val price : Int,
) {
    fun getKgWater() : Int {
        return newWater - preWater
    }

    fun getMoney() : Int {
        return getKgWater() * price
    }
}