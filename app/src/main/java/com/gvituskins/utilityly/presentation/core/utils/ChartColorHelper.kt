package com.gvituskins.utilityly.presentation.core.utils

import androidx.compose.ui.graphics.Color

object ChartColorHelper {

    private val defaultColors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Magenta,
        Color.Cyan, Color.Yellow, Color.Gray, Color(0xFFFFA500)
    )

    private fun generateColor(index: Int, total: Int): Color {
        val hue = (index * 360f / total) % 360
        return Color.hsv(hue, 0.7f, 0.9f)
    }

    fun getLineColor(index: Int): Color {
        return if (index < defaultColors.size) {
            defaultColors[index]
        } else {
            generateColor(index = index, total = index + 5)
        }
    }
}
