package com.gvituskins.utilityly.di

import android.content.Context
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideDataStoreUtil(@ApplicationContext context: Context): DataStoreUtil = DataStoreUtil(context)
}
