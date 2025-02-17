package com.gvituskins.utilityly.di

import com.gvituskins.utilityly.data.repositories.UtilityRepositoryImpl
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindUtilityRepository(impl: UtilityRepositoryImpl): UtilityRepository
}
