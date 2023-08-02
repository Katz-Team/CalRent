package vn.com.gatrong.calculaterent.data.Entity

import androidx.compose.ui.tooling.preview.Preview
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.com.gatrong.calculaterent.model.DefaultSetting

@Entity
data class DefaultSettingEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo
    val timeNotification: Long = 0,
    @ColumnInfo
    val rentHouse: Long = 0,
    @ColumnInfo
    val rentElect: Long = 0,
    @ColumnInfo
    val rentWater: Long = 0,
) {
    constructor(defaultSetting: DefaultSetting) :
            this(defaultSetting.id,
        defaultSetting.timeNotification,
        defaultSetting.rentHouse,
        defaultSetting.rentElect,
        defaultSetting.rentWater)

    fun toDefaultSetting() : DefaultSetting {
        return DefaultSetting(id = id, timeNotification = timeNotification, rentHouse = rentHouse, rentElect = rentElect, rentWater =  rentWater, arrayListOf())
    }
}