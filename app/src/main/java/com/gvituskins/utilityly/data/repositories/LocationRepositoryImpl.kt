package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.LocationDao
import com.gvituskins.utilityly.data.mappers.toLocation
import com.gvituskins.utilityly.data.mappers.toLocationEntity
import com.gvituskins.utilityly.data.utils.dbCall
import com.gvituskins.utilityly.domain.models.common.EitherEmpty
import com.gvituskins.utilityly.domain.models.locations.Location
import com.gvituskins.utilityly.domain.repositories.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao
) : LocationRepository {
    override fun getAllLocations(): Flow<List<Location>> {
        return locationDao.getAllLocations().map { locations ->
            locations.map { it.toLocation() }
        }
    }

    override suspend fun addNewLocation(location: Location): EitherEmpty {
        return dbCall {
            locationDao.addLocation(location.toLocationEntity())
        }
    }

    override suspend fun updateLocation(location: Location): EitherEmpty {
        return dbCall {
            locationDao.updateLocation(location.toLocationEntity())
        }
    }

    override suspend fun deleteLocation(location: Location): EitherEmpty {
        return dbCall {
            locationDao.deleteLocation(location.toLocationEntity())
        }
    }
}
