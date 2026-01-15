package com.example.mishirt_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mishirt_movil.ui.theme.Mishirt_movilTheme
import androidx.compose.runtime.collectAsState
import com.example.mishirt_movil.view.HomeScreen
import com.example.mishirt_movil.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mishirt_movil.view.SettingsScreen
import com.example.mishirt_movil.viewmodel.SettingsViewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsVm: SettingsViewModel = viewModel()
            val settingsState = settingsVm.uiState.collectAsState().value

            Mishirt_movilTheme(
                darkTheme = settingsState.isDarkTheme
            ) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        val homeVm: HomeViewModel = viewModel()
                        val homeState = homeVm.uiState.collectAsState().value

                        HomeScreen(
                            state = homeState,
                            onProfileClick = homeVm::onProfileClick,
                            onSettingsClick = { navController.navigate("settings") }
                        )
                    }

                    composable("settings") {
                        SettingsScreen(
                            state = settingsState,
                            onBack = { navController.popBackStack() },
                            onToggleTheme = settingsVm::toggleTheme,
                            onToggleNotifications = settingsVm::toggleNotifications
                        )
                    }
                }
            }
        }

    }
}
