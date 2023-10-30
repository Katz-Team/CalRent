package vn.com.gatrong.calculaterent.view.calScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.com.gatrong.calculaterent.R
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.ElectricityBill
import vn.com.gatrong.calculaterent.model.Surcharge
import vn.com.gatrong.calculaterent.model.WaterBill
import vn.com.gatrong.calculaterent.usecase.DatabaseUsecase

class CalViewModel : ViewModel() {

    private val kgElectNow = MutableStateFlow("0")
    private val kgElectNowMessageError = MutableStateFlow("")

    private val kgWaterNow = MutableStateFlow("0")
    private val kgWaterNowMessageError = MutableStateFlow("")

    private val kgElectPre = MutableStateFlow("0")
    private val kgElectPreMessageError = MutableStateFlow("")

    private val kgWaterPre = MutableStateFlow("0")
    private val kgWaterPreMessageError = MutableStateFlow("")

    private val moneyRoom = MutableStateFlow("0")

    private val priceElect = MutableStateFlow("0")

    private val priceWater = MutableStateFlow("0")

    private val surcharges = mutableStateListOf<MutableStateFlow<String>>()

    private val preTime = MutableStateFlow(System.currentTimeMillis())

    private val nowTime = MutableStateFlow(System.currentTimeMillis())

    private val databaseUsecase = DatabaseUsecase()

    fun setKgElectNow(value : String) {
        kgElectNow.value = value
        if (kgElectNow.value.isNotEmpty() && kgElectPre.value.isNotEmpty()) {
            if (kgElectNow.value.toInt() < kgElectPre.value.toInt()) {
                kgElectNowMessageError.value = R.string.not_smaller_than_last_month.toString()
            } else {
                kgElectNowMessageError.value = ""
                kgElectPreMessageError.value = ""
            }
        }
    }

    fun setKgElectPre(value : String) {
        kgElectPre.value = value
        if (kgElectNow.value.isNotEmpty() && kgElectPre.value.isNotEmpty()) {
            if (kgElectNow.value.toInt() < kgElectPre.value.toInt()) {
                kgElectPreMessageError.value = R.string.not_bigger_than_last_month.toString()
            } else {
                kgElectPreMessageError.value = ""
                kgElectNowMessageError.value = ""
            }
        }
    }

    fun setKgWaterNow(value : String) {
        kgWaterNow.value = value
        if (kgWaterNow.value.isNotEmpty() && kgWaterPre.value.isNotEmpty()) {
            if (kgWaterNow.value.toInt() < kgWaterPre.value.toInt()) {
                kgWaterNowMessageError.value = R.string.not_smaller_than_last_month.toString()
            } else {
                kgWaterPreMessageError.value = ""
                kgWaterNowMessageError.value = ""
            }
        }
    }

    fun setKgWaterPre(value : String) {
        kgWaterPre.value = value
        if (kgWaterNow.value.isNotEmpty() && kgWaterPre.value.isNotEmpty()) {
            if (kgWaterNow.value.toInt() < kgWaterPre.value.toInt()) {
                kgWaterPreMessageError.value = R.string.not_bigger_than_last_month.toString()
            } else {
                kgWaterPreMessageError.value = ""
                kgWaterNowMessageError.value = ""
            }
        }
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
    fun getKgElectNowMessageError() = kgElectNowMessageError.asStateFlow()

    fun getKgWaterNow() = kgWaterNow.asStateFlow()
    fun getKgWaterNowMessageError() = kgWaterNowMessageError.asStateFlow()
    fun getKgElectPre() = kgElectPre.asStateFlow()
    fun getKgElectPreMessageError() = kgElectPreMessageError.asStateFlow()

    fun getKgWaterPre() = kgWaterPre.asStateFlow()
    fun getKgWaterPreMessageError() = kgWaterPreMessageError.asStateFlow()

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

    fun isAllInfoValid() : Boolean {
        var isValid = true

        if (
            kgElectNow.value.isEmpty() ||
            kgElectPre.value.isEmpty() ||
            kgWaterNow.value.isEmpty() ||
            kgWaterPre.value.isEmpty() ||
            moneyRoom.value.isEmpty() ||
            priceElect.value.isEmpty() ||
            priceWater.value.isEmpty()
        ) {
            return false
        }

        if (kgElectPre.value.toInt() > kgElectNow.value.toInt()) {
            kgElectNowMessageError.value = R.string.not_smaller_than_last_month.toString()
            isValid = false
        } else {
            kgElectNowMessageError.value = ""
        }

        if (kgWaterPre.value.toInt() > kgWaterNow.value.toInt()) {
            kgWaterNowMessageError.value = R.string.not_smaller_than_last_month.toString()
            isValid = false
        } else {
            kgWaterNowMessageError.value = ""
        }

        return isValid
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