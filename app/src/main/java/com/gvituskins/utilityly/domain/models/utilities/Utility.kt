package com.gvituskins.utilityly.domain.models.utilities

data class Utility(
    val id: Int,
    val name: String,
    val description: String?,
    val date: String,
    val isPaid: Boolean,
    val amount: Double
)
