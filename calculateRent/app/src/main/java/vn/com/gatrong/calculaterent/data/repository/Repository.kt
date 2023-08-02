package vn.com.gatrong.calculaterent.data.repository

import kotlinx.coroutines.flow.Flow
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.DefaultSetting

interface Repository {

    fun insertBill(bill: Bill)

    fun removeBill(bill: Bill)

    suspend fun getBills() : List<Bill>

    suspend fun getBillLast() : Bill

    suspend fun getDefaults() : List<DefaultSetting>

    suspend fun getDefaultSetting() : DefaultSetting

    fun getFlowBills() : Flow<List<Bill>>

    fun modifiedSetting(defaultSetting: DefaultSetting)

    fun close()
}