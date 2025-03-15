package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.LocationEntity
import com.gvituskins.utilityly.domain.models.locations.Location

fun Location.toLocationEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name
    )
}

fun LocationEntity.toLocation(): Location {
    return Location(
        id = id,
        name = name
    )
}
