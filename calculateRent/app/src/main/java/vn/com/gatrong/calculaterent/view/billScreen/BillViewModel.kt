package vn.com.gatrong.calculaterent.view.billScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.usecase.DatabaseUsecase

class BillViewModel : ViewModel() {
    private val databaseUsecase = DatabaseUsecase()

    fun insertBill( bill : Bill ) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseUsecase.insertBill(bill)
        }
    }
}