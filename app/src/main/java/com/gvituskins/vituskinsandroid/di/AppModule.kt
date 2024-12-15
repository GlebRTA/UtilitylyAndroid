package com.gvituskins.vituskinsandroid.di

import android.content.Context
import androidx.room.Room
import com.gvituskins.vituskinsandroid.data.db.UHDatabase
import com.gvituskins.vituskinsandroid.data.db.dao.UtilityDao
import com.gvituskins.vituskinsandroid.data.repositories.UtilityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): UHDatabase {
        return Room.databaseBuilder(
            applicationContext,
            UHDatabase::class.java,
            "utility_helper"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUtilityDao(appDatabase: UHDatabase): UtilityDao {
        return appDatabase.utilityDao()
    }

    @Provides
    @Singleton
    fun provideUtilityRepository(utilityDao: UtilityDao): UtilityRepository {
        return UtilityRepository(utilityDao = utilityDao)
    }
}
