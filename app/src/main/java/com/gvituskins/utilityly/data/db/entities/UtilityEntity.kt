package com.gvituskins.utilityly.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import java.util.Date

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
    val dateCreated: Date,
    val paidStatus: PaidStatus,
    val dueDate: Date,
    val datePaid: Date?,
    val previousUtilityId: Int?,
)

data class CategoryWithUtilities(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val utilities: List<UtilityEntity>
)

data class CompanyWithUtilities(
    @Embedded val category: CompanyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "companyId"
    )
    val utilities: List<UtilityEntity>
)
