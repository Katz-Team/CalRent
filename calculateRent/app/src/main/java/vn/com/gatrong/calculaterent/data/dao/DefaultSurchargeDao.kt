package vn.com.gatrong.calculaterent.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import vn.com.gatrong.calculaterent.data.Entity.DefaultSettingEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSurchargeEntity

@Dao
interface DefaultSurchargeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultSurcharge(defaultSurchargeEntity: DefaultSurchargeEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDefaultSurcharge(defaultSurchargeEntity: DefaultSurchargeEntity)

    @Delete
    suspend fun deleteDefaultSurcharge(defaultSurchargeEntity: DefaultSurchargeEntity)

    @Query("SELECT * FROM DefaultSurchargeEntity")
    suspend fun getAllDefaultSurcharge() : List<DefaultSurchargeEntity>

    @Query("DELETE FROM DefaultSurchargeEntity")
    suspend fun clear()
}