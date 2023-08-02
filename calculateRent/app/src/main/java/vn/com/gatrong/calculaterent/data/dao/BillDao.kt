package vn.com.gatrong.calculaterent.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import vn.com.gatrong.calculaterent.data.Entity.BillEntity
import vn.com.gatrong.calculaterent.data.Entity.SurchargeEntity

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(billEntity: BillEntity) : Long

    @Update
    suspend fun updateBill(billEntity: BillEntity)

    @Delete
    suspend fun deleteBill(billEntity: BillEntity)

    @Query("SELECT * FROM BillEntity")
    suspend fun getAllBill() : List<BillEntity>

    @Query("SELECT * FROM BillEntity JOIN SurchargeEntity ON BillEntity.id = SurchargeEntity.idBill")
    fun loadBillAndSurcharge() : Flow<Map<BillEntity, List<SurchargeEntity>>>
}