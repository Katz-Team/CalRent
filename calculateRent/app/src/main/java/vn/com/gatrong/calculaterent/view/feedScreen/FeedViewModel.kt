package vn.com.gatrong.calculaterent.view.feedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.com.gatrong.calculaterent.data.repository.Repository
import vn.com.gatrong.calculaterent.data.repository.RepositoryImpl
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.DefaultSetting
import vn.com.gatrong.calculaterent.model.Room
import vn.com.gatrong.calculaterent.navigation.NavigateState
import vn.com.gatrong.calculaterent.navigation.Navigator

class FeedViewModel : ViewModel() {
    private val repository : Repository = RepositoryImpl.getInstance()

    private var setting = MutableStateFlow(DefaultSetting())

    private var room = MutableStateFlow(Room())

    init {
        viewModelScope.launch {
            if (!checkHaveDefaultSetting()) {
                Navigator.navigateTo(NavigateState(NavigateState.INNIT_SCREEN))
            }
        }

        viewModelScope.launch {
            repository.getFlowBills().stateIn(viewModelScope).collect { bills ->
                room.value = Room(bills)
            }
        }
    }

    fun getRoom() = room.asStateFlow()

    suspend fun checkHaveDefaultSetting() : Boolean {
        return viewModelScope.async {
            val settings = repository.getDefaults()
            if (settings.isEmpty()) {
                return@async false
            }
            val data = settings.first()
            setting.value = DefaultSetting(data.id,data.timeNotification,data.rentHouse,data.rentElect,data.rentWater,data.defaultSurcharges)
            return@async true
        }.await()
    }

    fun deleteBill(bill: Bill) {
        repository.removeBill(bill = bill)
    }
}