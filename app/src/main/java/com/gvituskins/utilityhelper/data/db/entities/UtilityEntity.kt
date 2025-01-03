package com.gvituskins.utilityhelper.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utility")
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String?,
    val date: String,
    val isPaid: Boolean,
    val amount: Double,
)
