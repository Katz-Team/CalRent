package vn.com.gatrong.calculaterent.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import vn.com.gatrong.calculaterent.data.Entity.SurchargeEntity

@Dao
interface SurchargeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurcharge(surchargeEntity: SurchargeEntity) : Long

    @Update
    suspend fun updateSurcharge(surchargeEntity: SurchargeEntity)

    @Delete
    suspend fun deleteSurcharge(surchargeEntity: SurchargeEntity)

    @Query("SELECT * FROM SurchargeEntity")
    suspend fun getAllSurcharge() : List<SurchargeEntity>

    @Query("SELECT * FROM SurchargeEntity WHERE idBill = :idBill")
    suspend fun getAllSurcharge(idBill : Long) : List<SurchargeEntity>
}