package com.gvituskins.vituskinsandroid.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gvituskins.vituskinsandroid.domain.models.Utility

@Entity(tableName = "utility")
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?,
    val date: String,
    val isPaid: Boolean
)

fun UtilityEntity.toUtility(): Utility {
    return Utility(
        id = id,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid
    )
}

fun Utility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = id ?: 0,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid
    )
}
