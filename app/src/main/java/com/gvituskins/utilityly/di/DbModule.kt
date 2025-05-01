package com.gvituskins.utilityly.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gvituskins.utilityly.data.db.UlyDatabase
import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.db.dao.CompanyDao
import com.gvituskins.utilityly.data.db.dao.LocationDao
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.mappers.toLocationEntity
import com.gvituskins.utilityly.domain.models.locations.Location
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
        locationDao: Provider<LocationDao>
    ): UlyDatabase {
        return Room.databaseBuilder(
            applicationContext,
            UlyDatabase::class.java,
            "utilityly"
        )
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            locationDao.get().addLocation(
                                Location(
                                    id = 1,
                                    name = "Home"
                                ).toLocationEntity()
                            )
                        }
                    }
                }
            )
            .build()
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

    @Provides
    @Singleton
    fun provideCompanyDao(appDatabase: UlyDatabase): CompanyDao {
        return appDatabase.companyDao()
    }
}
