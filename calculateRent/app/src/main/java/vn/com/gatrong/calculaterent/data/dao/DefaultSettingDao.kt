package vn.com.gatrong.calculaterent.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import vn.com.gatrong.calculaterent.data.Entity.BillEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSettingEntity

@Dao
interface DefaultSettingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultSetting(defaultSettingEntity: DefaultSettingEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDefaultSetting(defaultSettingEntity: DefaultSettingEntity)

    @Delete
    suspend fun deleteDefaultSetting(defaultSettingEntity: DefaultSettingEntity)

    @Query("SELECT * FROM DefaultSettingEntity")
    suspend fun getAllDefaultSetting() : List<DefaultSettingEntity>

    @Query("DELETE FROM DefaultSettingEntity")
    suspend fun clear()

}