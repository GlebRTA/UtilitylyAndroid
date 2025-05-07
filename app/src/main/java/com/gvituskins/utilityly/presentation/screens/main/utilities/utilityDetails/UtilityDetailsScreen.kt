package com.gvituskins.utilityly.presentation.screens.main.utilities.utilityDetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.dialogs.UlyAlertDialog
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.core.utils.UiConstants
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
import com.gvituskins.utilityly.presentation.core.utils.roundToStr
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
fun UtilityDetailsScreen(
    navigateBack: () -> Unit,
    viewModel: UtilityDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.label.collectAsOneTimeEvent { event ->
        when (event) {
            UtilityDetailsOTM.NavigateBack -> navigateBack()
        }
    }

    UlyScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            UlyDefaultTopAppBar(
                title = stringResource(R.string.utility),
                navigateBack = navigateBack,
                actions = {
                    IconButton(onClick = { viewModel.updateModal(UtilityDetailsModal.Delete) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_utility)
                        )
                    }
                }
            )
        }
    ) { innerPaddings ->
        val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(innerPaddings)
                .padding(UlyTheme.spacing.medium)
        ) {
            TitleCard(
                title = "${uiState.utility?.location?.name ?: ""} ${uiState.utility?.category?.name ?: ""}",
                month = uiState.utility?.dueDate?.format(monthFormatter) ?: "-",
                due = uiState.utility?.dueDate?.let { dueDate ->
                    val dif = ChronoUnit.DAYS.between(LocalDate.now(), dueDate)

                    val uiDif = abs(dif)
                    if (dif > 0) {
                        "due in $uiDif days"
                    } else if (dif == 0L) {
                        stringResource(R.string.today)
                    } else {
                        "overdue by $uiDif days"
                    }
                } ?: "-",
                company = uiState.utility?.company?.name ?: "-",
                amount = uiState.utility?.amount?.let { "${UiConstants.CURRENCY_SIGN}$it" } ?: "-",
                isPaid = uiState.utility?.paidStatus?.isPaid == true,
                onPaidClick = { viewModel.changePaidStatus() }
            )

            VerticalSpacer(UlyTheme.spacing.xLarge)

            DetailsSection(
                coast = uiState.detailsCoast,
                compare = uiState.detailsCompare
            )

            VerticalSpacer(UlyTheme.spacing.xLarge)

            val params = uiState.utility?.category?.parameters
            if (!params.isNullOrEmpty()) {
                CategoryParametersTable(
                    currentParameters = params,
                    prevParameters = uiState.prevUtility?.category?.parameters
                )
            }
        }
    }

    when (uiState.currentModal) {
        UtilityDetailsModal.Delete -> {
            UlyAlertDialog(
                titleText = "Do you want to delete utility?",
                onDismissRequest = { viewModel.updateModal(UtilityDetailsModal.None) },
                confirmText = stringResource(R.string.delete),
                onConfirmClicked = { viewModel.deleteUtility() },
                dismissText = stringResource(R.string.cancel)
            )
        }
        UtilityDetailsModal.None -> {  }
    }
}

@Composable
private fun CategoryParametersTable(
    currentParameters: List<CategoryParameter>?,
    prevParameters: List<CategoryParameter>?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = UlyTheme.spacing.border,
                color = UlyTheme.colors.outline,
                shape = UlyTheme.shapes.xSmall
            )
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)) {
            TableRow(
                name = stringResource(R.string.name),
                previous = stringResource(R.string.previous),
                current = stringResource(R.string.current),
                background = UlyTheme.colors.outline.copy(alpha = 0.3f),
            )
        }

        currentParameters?.forEachIndexed { index, parameter ->
            HorizontalDivider()
            TableRow(
                name = parameter.name,
                previous = prevParameters?.getOrNull(index)?.value ?: "-",
                current = parameter.value ?: "-"
            )
        }
    }
}

@Composable
private fun TableRow(
    name: String,
    previous: String,
    current: String,
    background: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(background)
            .padding(vertical = UlyTheme.spacing.small)
    ) {
        Text(
            text = name,
            modifier = Modifier
                .weight(2f)
                .padding(start = UlyTheme.spacing.medium)
        )

        VerticalDivider()

        Text(
            text = previous,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        VerticalDivider()

        Text(
            text = current,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TitleCard(
    title: String,
    month: String,
    due: String,
    company: String,
    amount: String,
    isPaid: Boolean,
    onPaidClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = UlyTheme.spacing.border,
                color = UlyTheme.colors.outline,
                shape = UlyTheme.shapes.xSmall
            )
            .padding(UlyTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = UlyTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )

        VerticalSpacer(UlyTheme.spacing.large)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = month,
                style = UlyTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = due,
                fontStyle = FontStyle.Italic
            )
        }

        VerticalSpacer(UlyTheme.spacing.medium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = company,
                style = UlyTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = amount,
                style = UlyTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }

        VerticalSpacer(UlyTheme.spacing.xLarge)

        UlyFilledTonalButton(
            onClick = onPaidClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(if (isPaid) R.string.make_as_unpaid else R.string.make_as_paid))
        }
    }
}

@Composable
private fun DetailsSection(
    coast: Float,
    compare: CompareAmount?
) {
    val coastAnimProgress = remember {
        Animatable(0f)
    }

    val compareAnimProgress = remember {
        Animatable(0f)
    }

    val compareAmountAnimProgress = remember {
        Animatable(0f)
    }

    LaunchedEffect(coast) {
        launch {
            coastAnimProgress.animateTo(coast, tween(1000))
        }
    }

    LaunchedEffect(compare) {
        compare?.let {
            launch {
                compareAnimProgress.animateTo(compare.percentDif, tween(1000))
            }
            launch {
                compareAmountAnimProgress.animateTo(compare.amountDif, tween(1000))
            }
        }
    }

    TextInputItem(
        title = stringResource(R.string.details),
        titleStyle = UlyTheme.typography.displaySmall
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = UlyTheme.spacing.border,
                    color = UlyTheme.colors.outline,
                    shape = UlyTheme.shapes.xSmall
                )
                .padding(UlyTheme.spacing.medium)
        ) {
            DetailsInfoRow(
                startText = stringResource(R.string.coast),
                endText = (coastAnimProgress.value * 100).roundToStr(1) + "%",
                progress = { coastAnimProgress.value },
                progressStartFromCenter = false,
                modifier = Modifier.fillMaxWidth()
            )

            compare?.let {
                VerticalSpacer(UlyTheme.spacing.medium)

                DetailsInfoRow(
                    startText = stringResource(R.string.compare_with_last_payment),
                    endText = compareAmountAnimProgress.value.roundToStr(n = 1, alwaysShowSign = true) + UiConstants.CURRENCY_SIGN,
                    progress = { compareAnimProgress.value },
                    progressStartFromCenter = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun DetailsInfoRow(
    startText: String,
    endText: String,
    progress: () -> Float,
    progressStartFromCenter: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = startText,
            style = UlyTheme.typography.titleMedium,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1.5f)
        )

        HorizontalSpacer(UlyTheme.spacing.xSmall)

        Box(
            modifier = Modifier
                .height(12.dp)
                .width(120.dp)
                .weight(3f)
                .clip(UlyTheme.shapes.xSmall)
                .border(
                    width = 0.5.dp,
                    color = UlyTheme.colors.outline,
                    shape = UlyTheme.shapes.xSmall
                )
                .drawBehind {
                    val progressResult = progress()
                    if (progressStartFromCenter) {
                        drawRect(
                            color = if (progressResult > 0) Color.Red else Color.Green,
                            size = size.copy(width = size.width / 2 * progressResult),
                            topLeft = Offset(x = size.width / 2, y = 0f)
                        )
                    } else {
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Green,
                                    Color.Yellow,
                                    Color.Red
                                )
                            ),
                            size = size.copy(width = size.width * progressResult),
                        )
                    }
                }
        )

        HorizontalSpacer(UlyTheme.spacing.mediumLarge)

        Text(
            text = endText,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.weight(0.8f)
        )
    }
}

@Preview
@Composable
private fun UtilityDetailsScreenPreview() {
    UtilitylyTheme {
        UtilityDetailsScreen(navigateBack = {})
    }
}