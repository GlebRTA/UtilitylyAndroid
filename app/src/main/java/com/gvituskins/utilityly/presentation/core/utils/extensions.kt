package com.gvituskins.utilityly.presentation.core.utils

fun Double.roundToStr(n: Int = 2): String {
    return String.format("%.${n}f", this)
}