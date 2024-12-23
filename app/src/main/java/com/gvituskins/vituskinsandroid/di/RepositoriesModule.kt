package com.gvituskins.vituskinsandroid.di

import com.gvituskins.vituskinsandroid.data.repositories.UtilityRepositoryImpl
import com.gvituskins.vituskinsandroid.domain.repositories.UtilityRepository
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
