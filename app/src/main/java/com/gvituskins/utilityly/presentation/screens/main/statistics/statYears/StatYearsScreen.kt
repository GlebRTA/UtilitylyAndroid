package com.gvituskins.utilityly.presentation.screens.main.statistics.statYears

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.rememberDropDownTextFieldSate
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun StatYearsScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = UlyTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UlyDropDownTextField(
            state = rememberDropDownTextFieldSate(
                initialValue = "Water",
                options = listOf("Water", "Electric")
            ),
            textBuilder = { it },
            modifier = Modifier.padding(
                top = UlyTheme.spacing.medium,
                bottom = UlyTheme.spacing.xxLarge
            )
        )

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            data = remember {
                listOf(
                    Line(
                        label = "Windows",
                        values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                        color = SolidColor(Color(0xFF23af92)),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                    )
                )
            },
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),
            indicatorProperties = HorizontalIndicatorProperties(enabled = false),
            labelHelperProperties = LabelHelperProperties(enabled = false),
        )

        VerticalSpacer(UlyTheme.spacing.xLarge)
        YearRow(
            color = Color.Red,
            state = rememberDropDownTextFieldSate(
                initialValue = 2024,
                options = listOf(2024, 2025)
            )
        )
        VerticalSpacer(UlyTheme.spacing.medium)
        YearRow(
            color = Color.Green,
            state = rememberDropDownTextFieldSate(
                initialValue = null,
                options = listOf(2024, 2025)
            )
        )
    }

}

@Composable
private fun ColumnScope.YearRow(
    color: Color,
    state: DropDownTextFieldState<Int?>
) {
    Row(
        modifier = Modifier
            .align(Alignment.Start)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(color)
        )

        HorizontalSpacer(UlyTheme.spacing.medium)

        UlyDropDownTextField(
            state = state,
            textBuilder = { it?.toString() ?: "Empty" },
            modifier = Modifier.width(180.dp)
        )
    }
}
