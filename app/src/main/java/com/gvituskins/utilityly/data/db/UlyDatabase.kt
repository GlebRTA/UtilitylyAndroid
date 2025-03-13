package com.gvituskins.utilityly.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterCategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterValueEntity
import com.gvituskins.utilityly.data.db.entities.UtilityEntity

@Database(
    entities = [UtilityEntity::class, CategoryEntity::class, ParameterCategoryEntity::class, ParameterValueEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class UlyDatabase : RoomDatabase() {

    abstract fun utilityDao(): UtilityDao

    abstract fun categoryDao(): CategoryDao
}
