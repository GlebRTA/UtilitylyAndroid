package com.gvituskins.utilityly.di

import android.content.Context
import androidx.room.Room
import com.gvituskins.utilityly.data.db.UlyDatabase
import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.db.dao.LocationDao
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): UlyDatabase {
        return Room.databaseBuilder(
            applicationContext,
            UlyDatabase::class.java,
            "utilityly"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUtilityDao(appDatabase: UlyDatabase): UtilityDao {
        return appDatabase.utilityDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: UlyDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: UlyDatabase): LocationDao {
        return appDatabase.locationDao()
    }
}
