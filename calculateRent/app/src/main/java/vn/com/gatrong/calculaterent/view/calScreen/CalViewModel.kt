package vn.com.gatrong.calculaterent.view.calScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.ElectricityBill
import vn.com.gatrong.calculaterent.model.Surcharge
import vn.com.gatrong.calculaterent.model.WaterBill
import vn.com.gatrong.calculaterent.usecase.DatabaseUsecase

class CalViewModel : ViewModel() {

    private val kgElectNow = MutableStateFlow("")

    private val kgWaterNow = MutableStateFlow("")

    private val kgElectPre = MutableStateFlow("")

    private val kgWaterPre = MutableStateFlow("")

    private val moneyRoom = MutableStateFlow("")

    private val priceElect = MutableStateFlow("")

    private val priceWater = MutableStateFlow("")

    private val surcharges = mutableStateListOf<MutableStateFlow<String>>()

    private val preTime = MutableStateFlow(System.currentTimeMillis())

    private val nowTime = MutableStateFlow(System.currentTimeMillis())

    private val databaseUsecase = DatabaseUsecase()

    fun setKgElectNow(value : String) {
        kgElectNow.value = value
    }

    fun setKgElectPre(value : String) {
        kgElectPre.value = value
    }

    fun setKgWaterNow(value : String) {
        kgWaterNow.value = value
    }

    fun setKgWaterPre(value : String) {
        kgWaterPre.value = value
    }

    fun setMoneyRoom(value : String) {
        moneyRoom.value = value
    }

    fun setPriceElect(value : String) {
        priceElect.value = value
    }

    fun setPriceWater(value : String) {
        priceWater.value = value
    }

    fun setSurcharges(index: Int, value : String) {
        surcharges.removeAt(index)
        surcharges.add(index,MutableStateFlow(value))
    }

    fun getKgElectNow() = kgElectNow.asStateFlow()

    fun getKgWaterNow() = kgWaterNow.asStateFlow()

    fun getKgElectPre() = kgElectPre.asStateFlow()

    fun getKgWaterPre() = kgWaterPre.asStateFlow()

    fun getMoneyRoom() = moneyRoom.asStateFlow()

    fun getPriceElect() = priceElect.asStateFlow()

    fun getPriceWater() = priceWater.asStateFlow()

    fun getSurcharges() = surcharges

    init {
        viewModelScope.launch {
            val bill = databaseUsecase.getLastBillTemp(0,0,System.currentTimeMillis())

            kgElectPre.value = bill.electricityBill.preElectric.toString()
            kgWaterPre.value = bill.waterBill.preWater.toString()

            kgElectNow.value = bill.electricityBill.newElectric.toString()
            kgWaterNow.value = bill.waterBill.newWater.toString()

            moneyRoom.value = bill.moneyRent.toString()
            priceElect.value = bill.electricityBill.price.toString()
            priceWater.value = bill.waterBill.price.toString()

            preTime.value = bill.timeFrom

            bill.surcharges.forEach {
                surcharges.add(MutableStateFlow(it.price.toString()))
            }
        }
    }

    fun insertBill(bill: Bill, onDone: (bill : Bill) -> Unit ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                databaseUsecase.insertBill(bill)
                withContext(Dispatchers.Main) {
                    onDone.invoke(databaseUsecase.getNewBill())
                }
            }

        }
    }

    fun getBill() : Bill {
        return Bill(
            electricityBill = ElectricityBill(
                preElectric = kgElectPre.value.toInt(),
                newElectric = kgElectNow.value.toInt(),
                price = priceElect.value.toInt()
            ),
            waterBill = WaterBill(
                preWater = kgWaterPre.value.toInt(),
                newWater = kgWaterNow.value.toInt(),
                price = priceWater.value.toInt()
            ),
            moneyRent = moneyRoom.value.toLong(),
            surcharges = surcharges.mapIndexed {
                    index, mutableStateFlow ->
                Surcharge(
                    id = index.toLong(),
                    price = mutableStateFlow.value.toInt(),
                )
            } as ArrayList<Surcharge>,
            timeFrom = preTime.value,
            timeTo = nowTime.value
        )
    }
}