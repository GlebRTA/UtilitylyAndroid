package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.LocalAnimatedContentScopeScope
import com.gvituskins.utilityly.presentation.LocalSharedTransitionScope
import com.gvituskins.utilityly.presentation.components.CategoryColorBox
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyOutlinedButton
import com.gvituskins.utilityly.presentation.components.CheckedIcon
import com.gvituskins.utilityly.presentation.core.utils.SharedTransitionKeys
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UtilityListCard(
    id: Int,
    name: String,
    amount: String,
    isPaid: Boolean,
    color: Color,
    onActionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = UlyTheme.shapes.small,
        border = BorderStroke(
            width = UlyTheme.spacing.border,
            color = UlyTheme.colors.outline
        ),
        colors = CardDefaults.cardColors(
            containerColor = UlyTheme.colors.background
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = UlyTheme.spacing.medium,
                    vertical = UlyTheme.spacing.mediumSmall
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CategoryColorBox(
                        size = 36.dp,
                        color = color
                    )
                    HorizontalSpacer(UlyTheme.spacing.small)
                    with(LocalSharedTransitionScope.current) {
                        Text(
                            text = name,
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(SharedTransitionKeys.utilityDetailsCategory(id)),
                                animatedVisibilityScope = LocalAnimatedContentScopeScope.current,
                            ),
                            style = UlyTheme.typography.titleLarge,
                        )
                    }
                }
                with(LocalSharedTransitionScope.current) {
                    Text(
                        text = amount,
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(SharedTransitionKeys.utilityDetailsAmount(id)),
                            animatedVisibilityScope = LocalAnimatedContentScopeScope.current,
                        ),
                        style = UlyTheme.typography.titleLarge
                    )
                }
            }

            VerticalSpacer(UlyTheme.spacing.large)

            Box(modifier = Modifier.fillMaxWidth()) {
                UlyOutlinedButton(
                    onClick = { onActionClicked() },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(180.dp)
                ) {
                    Text(
                        text = "Edit",
                        style = UlyTheme.typography.titleMedium
                    )
                }

                CheckedIcon(
                    isChecked = isPaid,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
@Preview
private fun UtilityListCardPreview() {
    UtilitylyTheme {
        UtilityListCard(
            id = 0,
            name = "Water",
            amount = "10.00$",
            isPaid = false,
            color = Color.Cyan,
            onActionClicked = {},
        )
    }
}
