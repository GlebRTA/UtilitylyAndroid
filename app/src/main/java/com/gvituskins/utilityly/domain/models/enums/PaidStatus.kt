package com.gvituskins.utilityly.domain.models.enums

enum class PaidStatus {
    PAID, UNPAID;

    fun otherwise(): PaidStatus {
        return when (this) {
            PAID -> UNPAID
            UNPAID -> PAID
        }
    }
}
