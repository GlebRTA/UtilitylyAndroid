package com.gvituskins.utilityly.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "parameter_value",
    foreignKeys = [
        ForeignKey(
            entity = ParameterCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryParameterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UtilityEntity::class,
            parentColumns = ["id"],
            childColumns = ["utilityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ParameterValueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryParameterId: Int,
    val value: String,
    val utilityId: Int
)
