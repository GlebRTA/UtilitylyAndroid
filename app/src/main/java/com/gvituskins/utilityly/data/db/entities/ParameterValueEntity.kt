package com.gvituskins.utilityly.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parameter_category")
data class ParameterValueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryParameterId: Int,
    val value: String,
    val utilityId: Int
)
