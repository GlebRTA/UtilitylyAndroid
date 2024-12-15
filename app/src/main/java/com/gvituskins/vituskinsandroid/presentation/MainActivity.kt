package com.gvituskins.vituskinsandroid.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.gvituskins.vituskinsandroid.presentation.bottomNavBar.UHBottomNavigationBar
import com.gvituskins.vituskinsandroid.presentation.main.paidUtilities.PaidNavGraph
import com.gvituskins.vituskinsandroid.presentation.main.paidUtilities.paidGraph
import com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.unpaidGraph
import com.gvituskins.vituskinsandroid.presentation.theme.LocalNavController
import com.gvituskins.vituskinsandroid.presentation.theme.UtilityHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UtilityHelperTheme {
                val navController = LocalNavController.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { UHBottomNavigationBar() },
                    contentWindowInsets = WindowInsets(0,0,0,0)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = PaidNavGraph,
                        modifier = Modifier.padding(it)
                    ) {
                        paidGraph(navController = navController)
                        unpaidGraph(navController = navController)
                    }
                }
            }
        }
    }
}
