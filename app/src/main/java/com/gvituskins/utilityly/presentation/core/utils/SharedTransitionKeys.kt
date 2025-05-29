package com.gvituskins.utilityly.presentation.core.utils

object SharedTransitionKeys {
    fun categoryColorBox(id: Int?): String = "categoryColorBox-${id ?: -1}"

    fun utilityDetailsCategory(id: Int?): String = "ud-category-${id ?: -1}"
    fun utilityDetailsAmount(id: Int?): String = "ud-amount-${id ?: -1}"
}
