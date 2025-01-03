package com.gvituskins.utilityhelper.di

import com.gvituskins.utilityhelper.data.repositories.UtilityRepositoryImpl
import com.gvituskins.utilityhelper.domain.repositories.UtilityRepository
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
