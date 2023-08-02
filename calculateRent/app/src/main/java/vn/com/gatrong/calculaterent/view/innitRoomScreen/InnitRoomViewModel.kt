package vn.com.gatrong.calculaterent.view.innitRoomScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.com.gatrong.calculaterent.data.repository.RepositoryImpl
import vn.com.gatrong.calculaterent.extensions.toDateString
import vn.com.gatrong.calculaterent.extensions.toLongPatternDDMMYYYY
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.DefaultSetting
import vn.com.gatrong.calculaterent.model.DefaultSurcharge
import vn.com.gatrong.calculaterent.model.ElectricityBill
import vn.com.gatrong.calculaterent.model.WaterBill
import vn.com.gatrong.calculaterent.navigation.NavigateState
import vn.com.gatrong.calculaterent.navigation.Navigator
import vn.com.gatrong.calculaterent.util.UtilRegex

class InnitRoomViewModel : ViewModel() {
    private val _stateInnit = MutableStateFlow<StateInnit>(StateInnit(StateInnit.STEP1))
    var stateInnit = _stateInnit.asStateFlow()

    val time = MutableStateFlow(System.currentTimeMillis().toDateString("dd.MM.yyyy"))
    val rentHouse = MutableStateFlow("")

    val rentElect = MutableStateFlow("")
    val kgElect = MutableStateFlow("")
    val rentWater = MutableStateFlow("")
    val kgWater = MutableStateFlow("")

    var defaultSurcharges = mutableStateListOf<MutableStateFlow<DefaultSurcharge>>()

    val enableButtonNext = mutableStateOf(false)

    init {

        viewModelScope.launch {
            rentHouse.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP1)
                    enableButtonNext.value = isStep1NotError()
            }
        }

        viewModelScope.launch {
            time.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP1)
                    enableButtonNext.value = isStep1NotError()
            }
        }

        viewModelScope.launch {
            rentElect.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP2)
                    enableButtonNext.value = isAllStep2NotEmpty()
            }
        }

        viewModelScope.launch {
            kgElect.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP2)
                    enableButtonNext.value = isAllStep2NotEmpty()
            }
        }

        viewModelScope.launch {
            rentWater.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP2)
                    enableButtonNext.value = isAllStep2NotEmpty()
            }
        }

        viewModelScope.launch {
            kgWater.collect { value ->
                if (stateInnit.value.step == StateInnit.STEP2)
                    enableButtonNext.value = isAllStep2NotEmpty()
            }
        }

        viewModelScope.launch {
            stateInnit.collect { state ->
                when(state.step) {
                    StateInnit.STEP1 -> {
                        enableButtonNext.value = isStep1NotError()
                    }
                    StateInnit.STEP2 -> {
                        enableButtonNext.value = isAllStep2NotEmpty()
                    }
                }

            }
        }

    }

    private fun isStep1NotError() : Boolean {
        return rentHouse.value.isNotEmpty() && UtilRegex.matchesFormatDate(time.value)
    }

    private fun isAllStep2NotEmpty() : Boolean {
        return rentElect.value.isNotEmpty() && kgElect.value.isNotEmpty() && rentWater.value.isNotEmpty() && kgWater.value.isNotEmpty()
    }

    fun nextStep() {
        when (_stateInnit.value.step) {
            StateInnit.STEP1 -> {
                _stateInnit.value = StateInnit(StateInnit.STEP2)
            }
            StateInnit.STEP2 -> {
                _stateInnit.value = StateInnit(StateInnit.STEP3)
            }
            StateInnit.STEP3 -> {

                val listSurcharges = ArrayList<DefaultSurcharge>()

                defaultSurcharges.map { defaultSurcharge ->
                    listSurcharges.add(defaultSurcharge.value)
                }

                RepositoryImpl.getInstance().modifiedSetting(
                    DefaultSetting(timeNotification = time.value.toLongPatternDDMMYYYY(),
                        rentHouse = rentHouse.value.toLong(),
                        rentWater = rentWater.value.toLong(),
                        rentElect = rentElect.value.toLong(),
                        defaultSurcharges = listSurcharges
                    )
                )

                RepositoryImpl.getInstance().insertBill(Bill(electricityBill = ElectricityBill(0,
                    newElectric = kgElect.value.toInt(), rentElect.value.toInt()),
                    waterBill = WaterBill(0, newWater = kgWater.value.toInt(),rentWater.value.toInt()),
                    timeTo = time.value.toLongPatternDDMMYYYY())
                )

                Navigator.navigateTo(NavigateState(NavigateState.FEED_SCREEN))
            }
            else -> {

            }
        }

    }

    fun preStep() {
        when (_stateInnit.value.step) {
            StateInnit.STEP1 -> {
            }

            StateInnit.STEP2 -> {
                _stateInnit.value = StateInnit(StateInnit.STEP1)
            }

            StateInnit.STEP3 -> {
                _stateInnit.value = StateInnit(StateInnit.STEP2)
            }

            else -> {

            }
        }
    }
}