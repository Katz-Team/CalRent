package vn.com.gatrong.calculaterent.data

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.com.gatrong.calculaterent.data.Entity.BillEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSettingEntity
import vn.com.gatrong.calculaterent.data.Entity.DefaultSurchargeEntity
import vn.com.gatrong.calculaterent.data.Entity.SurchargeEntity
import vn.com.gatrong.calculaterent.data.dao.BillDao
import vn.com.gatrong.calculaterent.data.dao.DefaultSettingDao
import vn.com.gatrong.calculaterent.data.dao.DefaultSurchargeDao
import vn.com.gatrong.calculaterent.data.dao.SurchargeDao

@Database(entities = [BillEntity::class, SurchargeEntity::class, DefaultSurchargeEntity::class, DefaultSettingEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "cal-rent"
    }

    abstract fun billDao(): BillDao

    abstract fun surchargeDao(): SurchargeDao

    abstract fun defaultSettingDao() : DefaultSettingDao

    abstract fun defaultSurchargeDao() : DefaultSurchargeDao
}