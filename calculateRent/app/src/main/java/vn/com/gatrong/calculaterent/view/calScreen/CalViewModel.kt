package vn.com.gatrong.calculaterent.view.calScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.usecase.DatabaseUsecase

class CalViewModel : ViewModel() {

    private val kgElect = MutableStateFlow("")

    private val kgWater = MutableStateFlow("")

    private val databaseUsecase = DatabaseUsecase()

    fun setElect(value : String) {
        kgElect.value = value
    }

    fun setWater(value : String) {
        kgWater.value = value
    }

    fun getElect() = kgElect.asStateFlow()

    fun getWater() = kgWater.asStateFlow()

    fun insertBill( onDone: (bill : Bill) -> Unit ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                databaseUsecase.insertBill(kgElect.value.toInt(),kgWater.value.toInt(),System.currentTimeMillis())
                withContext(Dispatchers.Main) {
                    onDone.invoke(databaseUsecase.getNewBill())
                }
            }

        }
    }

    suspend fun getLastBillTemp() : Bill {
        return viewModelScope.async {
            databaseUsecase.getLastBillTemp(kgElect.value.toInt(),kgWater.value.toInt(),System.currentTimeMillis())
        }.await()
    }
}