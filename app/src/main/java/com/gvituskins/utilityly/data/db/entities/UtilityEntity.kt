package com.gvituskins.utilityly.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import java.time.LocalDate

@Entity(
    tableName = "utility",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UtilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val companyId: Int?,
    val repeat: UtilityRepeat?,
    val amount: Double,
    val locationId: Int,
    val dateCreated: LocalDate,
    val paidStatus: PaidStatus,
    val dueDate: LocalDate,
    val datePaid: LocalDate?,
    val previousUtilityId: Int?,
)
