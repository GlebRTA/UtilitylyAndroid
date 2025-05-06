package com.gvituskins.utilityly.presentation.core.utils

fun Double.roundToStr(
    n: Int = 2,
    alwaysShowSign: Boolean = false
): String {
    return String.format("%${if (alwaysShowSign) "+" else ""}.${n}f", this)
}

fun Float.roundToStr(
    n: Int = 2,
    alwaysShowSign: Boolean = false
): String {
    return this.toDouble().roundToStr(n = n, alwaysShowSign = alwaysShowSign)
}