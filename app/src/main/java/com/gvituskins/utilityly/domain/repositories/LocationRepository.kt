package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.locations.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getAllLocations(): Flow<List<Location>>

    suspend fun addNewLocation(location: Location)

    suspend fun updateLocation(location: Location)

    suspend fun deleteLocation(location: Location)
}
