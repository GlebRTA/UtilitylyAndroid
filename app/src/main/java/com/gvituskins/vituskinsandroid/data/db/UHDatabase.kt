package com.gvituskins.vituskinsandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gvituskins.vituskinsandroid.data.db.dao.UtilityDao
import com.gvituskins.vituskinsandroid.data.db.entities.UtilityEntity

@Database(
    entities = [UtilityEntity::class],
    version = 1
)
abstract class UHDatabase : RoomDatabase() {

    abstract fun utilityDao(): UtilityDao
}
