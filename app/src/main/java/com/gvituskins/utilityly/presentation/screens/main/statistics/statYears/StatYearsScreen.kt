package com.gvituskins.utilityly.presentation.screens.main.statistics.statYears

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.rememberDropDownTextFieldSate
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun StatYearsScreen(
    viewModel: StatYearsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = UlyTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UlyDropDownTextField(
            state = uiState.categoryState,
            textBuilder = { it?.name ?: "" },
            modifier = Modifier.padding(
                top = UlyTheme.spacing.medium,
                bottom = UlyTheme.spacing.xxLarge
            ),
            onValueChanged = { category ->
                category?.let {
                    viewModel.initChart()
                }
            }
        )

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            data = uiState.yearWIthLine.map { it.line },
            animationMode = AnimationMode.Together(delayBuilder = { it * 500L }),
            indicatorProperties = HorizontalIndicatorProperties(
                enabled = true,
                textStyle = UlyTheme.typography.bodySmall.copy(color = UlyTheme.colors.onBackground)
            ),
            labelHelperProperties = LabelHelperProperties(enabled = false),
            curvedEdges = false,
            labelProperties = LabelProperties(
                enabled = true,
                labels = listOf("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"),
                builder = { modifier, label, shouldRotate, index ->
                    Text(
                        text = label,
                        style = UlyTheme.typography.bodySmall,
                    )
                }
            ),
            popupProperties = PopupProperties(
                mode = PopupProperties.Mode.PointMode(),
                textStyle = TextStyle.Default.copy(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        )

        uiState.yearWIthLine.forEach { yearWithLine ->
            VerticalSpacer(UlyTheme.spacing.xLarge)
            YearRow(
                color = yearWithLine.line.color,
                year = yearWithLine.year,
                options = uiState.availableYears,
                onValueChanged = { year ->
                    viewModel.updateLine(
                        id = yearWithLine.id,
                        newYear = year,
                    )
                },
                onDelete = { viewModel.removeLine(yearWithLine.year) }
            )
        }

        if (uiState.availableYears.isNotEmpty()) {
            VerticalSpacer(UlyTheme.spacing.xLarge)
            YearRow(
                color = SolidColor(Color.Transparent),
                year = null,
                options = uiState.availableYears,
                onValueChanged = { year ->
                    viewModel.addLine(year)
                },
                onDelete = null
            )
        }

        VerticalSpacer(40.dp)
    }
}

@Composable
private fun ColumnScope.YearRow(
    color: Brush,
    year: Int?,
    options: List<Int>,
    onValueChanged: (Int) -> Unit,
    onDelete: (() -> Unit)?
) {
    val dropDownTextFieldState = rememberDropDownTextFieldSate(
        initialValue = year,
        options = options
    )

    SideEffect {
        dropDownTextFieldState.updateValue(year)
        dropDownTextFieldState.updateOptions(options)
    }

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
            state = dropDownTextFieldState,
            textBuilder = { it?.toString() ?: "Empty" },
            modifier = Modifier.width(180.dp),
            onValueChanged = { year ->
                year?.let {
                    onValueChanged(it)
                }
            }
        )

        onDelete?.let {
            IconButton(
                onClick = onDelete
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove year")
            }
        }
    }
}
