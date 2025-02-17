package com.gvituskins.utilityly.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.db.entities.UtilityEntity

@Database(
    entities = [UtilityEntity::class],
    version = 1
)
abstract class UlyDatabase : RoomDatabase() {

    abstract fun utilityDao(): UtilityDao
}
