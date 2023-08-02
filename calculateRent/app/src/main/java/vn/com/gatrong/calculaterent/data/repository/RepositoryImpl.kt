package vn.com.gatrong.calculaterent.data.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import vn.com.gatrong.calculaterent.data.AppDatabase
import vn.com.gatrong.calculaterent.data.Entity.BillEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSettingEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSurchargeEntity
import vn.com.gatrong.calculaterent.data.Entity.SurchargeEntity
import vn.com.gatrong.calculaterent.data.dao.BillDao
import vn.com.gatrong.calculaterent.data.dao.DefaultSettingDao
import vn.com.gatrong.calculaterent.data.dao.DefaultSurchargeDao
import vn.com.gatrong.calculaterent.data.dao.SurchargeDao
import vn.com.gatrong.calculaterent.model.Bill
import vn.com.gatrong.calculaterent.model.DefaultSetting
import vn.com.gatrong.calculaterent.model.DefaultSurcharge
import vn.com.gatrong.calculaterent.model.Surcharge

class RepositoryImpl private constructor (
    private val billDao: BillDao,
    private val surchargeDao: SurchargeDao,
    private val defaultSettingDao: DefaultSettingDao,
    private val defaultSurchargeDao: DefaultSurchargeDao
) : Repository {

    val myScope = CoroutineScope(Dispatchers.IO)

    companion object {
        private var instance: RepositoryImpl? = null

        fun getInstance(application : Context? = null) : Repository {
            if (instance == null) {
                val database = Room.databaseBuilder(
                    application!!,
                    AppDatabase::class.java,
                    AppDatabase.DATABASE_NAME
                ).build()
                instance = RepositoryImpl(database.billDao(),database.surchargeDao(),database.defaultSettingDao(),database.defaultSurchargeDao())
            }
            return instance!!
        }
    }

    override fun insertBill(bill: Bill) {
        myScope.launch {
            bill.id = billDao.insertBill(BillEntity(bill))

            bill.surcharges.forEach { surcharge: Surcharge ->
                surcharge.id = surchargeDao.insertSurcharge(SurchargeEntity(surcharge,bill.id))
            }
        }
    }

    override fun removeBill(bill: Bill) {
        myScope.launch {
            bill.surcharges.forEach { surcharge: Surcharge ->
                surchargeDao.deleteSurcharge(SurchargeEntity(surcharge,bill.id))
            }

            billDao.deleteBill(BillEntity(bill))
        }
    }

    override suspend fun getBills(): List<Bill> {
        return myScope.async {
            val bills = ArrayList<Bill>()

            val billEntities = billDao.getAllBill()

            billEntities.forEach { billEntity: BillEntity ->

                val surchargeEntities = surchargeDao.getAllSurcharge(billEntity.id)

                val bill = billEntity.toBill()

                surchargeEntities.forEach { surchargeEntity: SurchargeEntity ->
                    bill.surcharges.add(surchargeEntity.toSurcharge())
                }

                bills.add(bill)
            }
            return@async bills
        }.await()
    }

    override suspend fun getBillLast(): Bill {
        return myScope.async {
            val bills = getBills()
            if (bills.isEmpty()) {
                return@async Bill()
            } else {
                return@async bills.last()
            }
        }.await()
    }

    private fun insertSetting(defaultSetting: DefaultSetting) {
        myScope.launch {

            defaultSetting.id = defaultSettingDao.insertDefaultSetting(DefaultSettingEntity(defaultSetting))

            defaultSetting.defaultSurcharges.forEach { defaultSurcharge ->
                defaultSurchargeDao.insertDefaultSurcharge(DefaultSurchargeEntity(defaultSurcharge,defaultSetting.id))
            }

        }
    }

    private fun updateSetting(defaultSetting: DefaultSetting) {
        myScope.launch {
            defaultSettingDao.updateDefaultSetting(DefaultSettingEntity(defaultSetting))

            defaultSetting.defaultSurcharges.forEach { defaultSurcharge ->
                defaultSurchargeDao.updateDefaultSurcharge(DefaultSurchargeEntity(defaultSurcharge,defaultSetting.id))
            }

        }
    }

    private fun removeSetting(defaultSetting: DefaultSetting) {
        myScope.launch {
            defaultSettingDao.deleteDefaultSetting(DefaultSettingEntity(defaultSetting))

            defaultSetting.defaultSurcharges.forEach { defaultSurcharge ->
                defaultSurchargeDao.deleteDefaultSurcharge(defaultSurchargeEntity = DefaultSurchargeEntity(defaultSurcharge,defaultSetting.id))
            }
        }
    }

    override suspend fun getDefaults(): List<DefaultSetting> {
        return myScope.async {
            val default = arrayListOf<DefaultSetting>()

            defaultSettingDao.getAllDefaultSetting().mapIndexed { index, defaultSettingEntity ->

                default.add(defaultSettingEntity.toDefaultSetting())

                defaultSurchargeDao.getAllDefaultSurcharge().map { defaultSurchargeEntity ->
                    default.get(index).defaultSurcharges.add(defaultSurchargeEntity.toDefaultSurcharge())
                }

                default
            }

            default
        }.await()
    }

    override suspend fun getDefaultSetting(): DefaultSetting {
        return myScope.async {
            val default = defaultSettingDao.getAllDefaultSetting().firstOrNull()?.toDefaultSetting() ?: DefaultSetting()

            defaultSurchargeDao.getAllDefaultSurcharge().map { defaultSurchargeEntity ->
                default.defaultSurcharges.add(defaultSurchargeEntity.toDefaultSurcharge())
            }

            default
        }.await()
    }

    override fun getFlowBills(): Flow<List<Bill>> {
        return billDao.loadBillAndSurcharge().map { result ->
            val lists = arrayListOf<Bill>()
            result.forEach { billAndSurcharge ->
                val bill = billAndSurcharge.key.toBill()
                billAndSurcharge.value.forEach { surchargeEntity ->
                    bill.surcharges.add(surchargeEntity.toSurcharge())
                }
                lists.add(bill)
            }
            lists
        }
    }

    override fun modifiedSetting(defaultSetting: DefaultSetting) {
        myScope.launch {
            val defaults = getDefaults()
            if (defaults.isNotEmpty()) {
                Log.d("Nick","update")
                updateSetting(defaultSetting = defaultSetting)
            } else {
                Log.d("Nick","insert")
                insertSetting(defaultSetting = defaultSetting)
            }
        }
    }

    override fun close() {
        myScope.cancel()
    }

}