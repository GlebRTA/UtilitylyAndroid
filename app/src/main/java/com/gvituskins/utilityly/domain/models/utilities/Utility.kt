package com.gvituskins.utilityly.domain.models.utilities

import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import com.gvituskins.utilityly.domain.models.locations.Location
import java.time.LocalDate

data class Utility(
    val id: Int = 0,
    val category: Category,
    val company: Company?,
    val repeat: UtilityRepeat?,
    val amount: Double,
    val location: Location,
    val dateCreated: LocalDate,
    val paidStatus: PaidStatus,
    val dueDate: LocalDate,
    val datePaid: LocalDate?,
)
