package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.common.EitherEmpty
import com.gvituskins.utilityly.domain.models.locations.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getAllLocations(): Flow<List<Location>>

    suspend fun addNewLocation(location: Location): EitherEmpty

    suspend fun updateLocation(location: Location): EitherEmpty

    suspend fun deleteLocation(location: Location): EitherEmpty
}
