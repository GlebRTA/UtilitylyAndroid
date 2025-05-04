package com.gvituskins.utilityly.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gvituskins.utilityly.presentation.components.animations.slideIntoOut
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme

@Composable
fun AnimatedCounter(
    counter: Int,
    onCounterChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onCounterChanged(counter - 1) }) {
            Text(text = "-")
        }

        AnimatedContent(
            targetState = counter,
            transitionSpec = { slideIntoOut() }
        ) {count ->
            Text(text = count.toString())
        }

        IconButton(onClick = { onCounterChanged(counter + 1) }) {
            Text(text = "+")
        }
    }
}

@Preview
@Composable
private fun AnimatedCounterPreview() {
    UtilitylyTheme {
        AnimatedCounter(counter = 5, onCounterChanged = {})
    }
}
