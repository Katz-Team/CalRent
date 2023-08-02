package vn.com.gatrong.calculaterent.model

data class DefaultSetting(
    var id : Long = 0,
    val timeNotification: Long = 0,
    var rentHouse: Long = 0,
    val rentElect: Long = 0,
    val rentWater: Long = 0,
    val defaultSurcharges: ArrayList<DefaultSurcharge> = arrayListOf()
) {
    fun toSurcharges() : ArrayList<Surcharge> {
        val list = ArrayList<Surcharge>()
        defaultSurcharges.forEach {
            list.add(it.toSurcharge())
        }
        return list
    }
}