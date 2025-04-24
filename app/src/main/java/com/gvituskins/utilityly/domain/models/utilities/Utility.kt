package com.gvituskins.utilityly.domain.models.utilities

import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import java.util.Date

data class Utility(
    val id: Int = 0,
    val category: Category,
    val company: Company?,
    val repeat: UtilityRepeat?,
    val amount: Double,
    val locationId: Int,
    val dateCreated: Date,
    val paidStatus: PaidStatus,
    val dueDate: Date,
    val datePaid: Date?,
    val previousUtilityId: Int?,
)
