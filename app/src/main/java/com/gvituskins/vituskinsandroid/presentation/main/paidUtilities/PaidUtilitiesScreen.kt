package com.gvituskins.vituskinsandroid.presentation.main.paidUtilities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaidUtilitiesScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Paid Utilities") }) },
        contentWindowInsets = WindowInsets(0,0,0,0)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = "AAAAaaaa",
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            Button(onClick = {  }) {
                Text(text = "Navigate to main 2")
            }
        }
    }
}