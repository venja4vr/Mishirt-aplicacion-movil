package com.example.mishirt_movil

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mishirt_movil.ui.theme.Mishirt_movilTheme
import com.example.mishirt_movil.view.AppTopBar
import com.example.mishirt_movil.view.AuthScreen
import com.example.mishirt_movil.view.HomeScreen
import com.example.mishirt_movil.view.ProfileScreen
import com.example.mishirt_movil.view.SettingsScreen
import com.example.mishirt_movil.viewmodel.HomeViewModel
import com.example.mishirt_movil.viewmodel.SettingsViewModel
import com.example.mishirt_movil.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            val userVm: UserViewModel = viewModel()
            val userState = userVm.uiState.collectAsState().value

            val settingsVm: SettingsViewModel = viewModel()
            val settingsState = settingsVm.uiState.collectAsState().value

            Mishirt_movilTheme(darkTheme = settingsState.isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        AppTopBar(
                            onTitleClick = {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            onProfileClick = {
                                if (userState.cuentaCreada) {
                                    navController.navigate("profile")
                                } else {
                                    navController.navigate("auth")
                                }
                            },
                            onSettingsClick = {
                                navController.navigate("settings")
                            }
                        )
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("home") {
                            val homeVm: HomeViewModel = viewModel()
                            val homeState = homeVm.uiState.collectAsState().value

                            HomeScreen(
                                state = homeState,
                                onProfileClick = {
                                    if (userState.cuentaCreada) {
                                        navController.navigate("profile")
                                    } else {
                                        navController.navigate("auth")
                                    }
                                },
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

                        composable("auth") {
                            AuthScreen(
                                state = userState,
                                onNombreChange = userVm::onNombreChange,
                                onCorreoChange = userVm::onCorreoChange,
                                onDireccionChange = userVm::onDireccionChange,
                                onClaveChange = userVm::onClaveChange,
                                onCrearCuenta = {
                                    userVm.crearCuenta()
                                    if (userVm.uiState.value.cuentaCreada) {
                                        navController.navigate("profile") {
                                            popUpTo("auth") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }

                        composable("profile") {
                            ProfileScreen(state = userState)
                        }
                    }
                }
            }
        }
    }
}
