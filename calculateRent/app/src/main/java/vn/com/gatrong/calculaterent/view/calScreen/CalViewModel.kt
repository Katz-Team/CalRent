package vn.com.gatrong.calculaterent.view.calScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.usecase.DatabaseUsecase

class CalViewModel : ViewModel() {

    private val kgElectNow = MutableStateFlow("")

    private val kgWaterNow = MutableStateFlow("")

    private val kgElectPre = MutableStateFlow("")

    private val kgWaterPre = MutableStateFlow("")

    private val moneyRoom = MutableStateFlow("")

    private val priceElect = MutableStateFlow("")

    private val priceWater = MutableStateFlow("")

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

    fun getKgElectNow() = kgElectNow.asStateFlow()

    fun getKgWaterNow() = kgWaterNow.asStateFlow()

    fun getKgElectPre() = kgElectPre.asStateFlow()

    fun getKgWaterPre() = kgWaterPre.asStateFlow()

    fun getMoneyRoom() = moneyRoom.asStateFlow()

    fun getPriceElect() = priceElect.asStateFlow()

    fun getPriceWater() = priceWater.asStateFlow()

    fun insertBill( onDone: (bill : Bill) -> Unit ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                databaseUsecase.insertBill(kgElectNow.value.toInt(),kgWaterNow.value.toInt(),System.currentTimeMillis())
                withContext(Dispatchers.Main) {
                    onDone.invoke(databaseUsecase.getNewBill())
                }
            }

        }
    }

    suspend fun getLastBillTemp() : Bill {
        return viewModelScope.async {
            databaseUsecase.getLastBillTemp(kgElectNow.value.toInt(),kgWaterNow.value.toInt(),System.currentTimeMillis())
        }.await()
    }
}