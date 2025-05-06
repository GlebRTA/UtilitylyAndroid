package com.gvituskins.utilityly.presentation.screens.main.statistics.statCategory

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.core.utils.UiConstants
import com.gvituskins.utilityly.presentation.core.utils.roundToStr
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun StatCategoryScreen(
    viewModel: StatCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UlyDropDownTextField(
            state = uiState.yearState,
            textBuilder = { it?.toString() ?: "" },
            modifier = Modifier.padding(
                top = UlyTheme.spacing.medium,
                bottom = UlyTheme.spacing.xxLarge
            ),
            onValueChanged = { viewModel.updateChartInfo() }
        )

        var data by remember {
            mutableStateOf(
                uiState.tableRows.map {
                    Pie(
                        label = it.name,
                        data = it.amount,
                        color = it.color
                    )
                }
            )
        }

        LaunchedEffect(uiState.tableRows) {
            data = uiState.tableRows.map {
                Pie(
                    label = it.name,
                    data = it.amount,
                    color = it.color
                )
            }
        }

        PieChart(
            modifier = Modifier.size(200.dp),
            data = data,
            onPieClick = {
                val pieIndex = data.indexOf(it)
                data = data.mapIndexed { mapIndex, pie ->
                    val selected = if (pieIndex == mapIndex) !pie.selected else false
                    pie.copy(selected = selected)
                }
            },
            selectedScale = 1.2f,
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(),
            colorAnimExitSpec = tween(),
            scaleAnimExitSpec = tween(),
            spaceDegreeAnimExitSpec = tween(),
            style = Pie.Style.Fill
        )

        VerticalSpacer(UlyTheme.spacing.xLarge)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = UlyTheme.spacing.medium)
        ) {
            data.forEachIndexed { i, pie ->
                val info = uiState.tableRows.getOrNull(i)
                TableRow(
                    color = pie.color,
                    name = pie.label.toString(),
                    percent = info?.percent?.roundToStr(1) + "%",
                    amount = "${UiConstants.CURRENCY_SIGN}${pie.data.roundToStr(1)}",
                    isSelected = pie.selected
                )
            }
        }
    }
}

@Composable
private fun TableRow(
    color: Color,
    name: String,
    percent: String,
    amount: String,
    isSelected: Boolean,
    background: Color = UlyTheme.colors.background,
) {
    val scaleAnim = animateFloatAsState(targetValue = if (isSelected) 1.05f else 1f,)
    val translationAnim = animateDpAsState(targetValue = if (isSelected) 6.dp else 0.dp)

    Surface(
        contentColor = UlyTheme.colors.onBackground,
        color = background,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .graphicsLayer {
                scaleX = scaleAnim.value
                scaleY = scaleAnim.value
                translationY = -translationAnim.value.toPx()
            },
        border = BorderStroke(
            width = UlyTheme.spacing.border,
            color = UlyTheme.colors.outline,
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(color)
            )

            VerticalDivider()

            Text(
                text = name,
                modifier = Modifier
                    .weight(2f)
                    .padding(
                        vertical = UlyTheme.spacing.small,
                        horizontal = UlyTheme.spacing.medium,
                    ),
                textAlign = TextAlign.Center
            )

            VerticalDivider()

            Text(
                text = percent,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            VerticalDivider()

            Text(
                text = amount,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}
