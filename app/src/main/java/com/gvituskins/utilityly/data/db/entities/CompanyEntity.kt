package com.gvituskins.utilityly.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String?,
    val phone: String?,
    val webPageUrl: String?,
    val email: String?
)
