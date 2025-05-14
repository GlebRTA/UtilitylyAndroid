package com.gvituskins.utilityly.presentation.core.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt

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

@OptIn(ExperimentalStdlibApi::class)
fun Color.toHex(): String = this.toArgb().toHexString()

fun String.fromHexToColor(): Color = Color("#${this}".toColorInt())