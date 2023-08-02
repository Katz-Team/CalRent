package vn.com.gatrong.calculaterent.model

data class Bill(
    var id : Long = 0,
    val moneyRent : Long = 0,
    val electricityBill : ElectricityBill = ElectricityBill(0,0,0),
    val waterBill: WaterBill = WaterBill(0,0,0),
    val timeFrom: Long = 0,
    val timeTo: Long = 0,
    val surcharges : ArrayList<Surcharge> = arrayListOf()
) {


    fun getTotalMoney() : Long {
        return moneyRent + electricityBill.getMoney() + waterBill.getMoney() + surcharges.sumOf { it.price }
    }

}