package vn.com.gatrong.calculaterent.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import vn.com.gatrong.calculaterent.model.DefaultSurcharge

@Entity(foreignKeys = arrayOf(ForeignKey(entity = DefaultSettingEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("idSetting"))))
data class DefaultSurchargeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo
    val idSetting : Long,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val price : Long,
) {
    constructor(defaultSurcharge: DefaultSurcharge, idSetting : Long) : this(defaultSurcharge.id,idSetting,defaultSurcharge.name,defaultSurcharge.price)

    fun toDefaultSurcharge() : DefaultSurcharge {
        return DefaultSurcharge(id,name, price)
    }
}
