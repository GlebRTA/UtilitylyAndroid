package com.gvituskins.utilityly.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import java.util.Date

@Entity(tableName = "utility")
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val companyId: Int?,
    val repeat: UtilityRepeat,
    val amount: Double,
    val locationId: Int,
    val dateCreated: Date?,
    val paidStatus: PaidStatus,
    val dueDate: Date?,
    val datePaid: Date?,
    val previousUtilityId: Int,
)
