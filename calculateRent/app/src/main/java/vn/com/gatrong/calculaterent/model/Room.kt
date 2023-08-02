package vn.com.gatrong.calculaterent.model

data class Room(
    val bills : List<Bill> = listOf()
) {
    fun getBill(index: Int) : Bill {
        return bills.get(index)
    }

    fun getFirstBill() : Bill {
        return bills.first()
    }
}