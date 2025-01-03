package com.gvituskins.utilityhelper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvituskins.utilityhelper.data.db.dao.UtilityDao
import com.gvituskins.utilityhelper.data.db.entities.UtilityEntity

@Database(
    entities = [UtilityEntity::class],
    version = 1
)
abstract class UHDatabase : RoomDatabase() {

    abstract fun utilityDao(): UtilityDao
}
