package com.example.mishirt_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mishirt_movil.repository.ProductsRepository
import com.example.mishirt_movil.ui.theme.Mishirt_movilTheme
import com.example.mishirt_movil.ui.theme.MossGreen
import com.example.mishirt_movil.view.AppTopBar
import com.example.mishirt_movil.view.AuthScreen
import com.example.mishirt_movil.view.CatalogScreen
import com.example.mishirt_movil.view.HomeScreen
import com.example.mishirt_movil.view.ProductDetailScreen
import com.example.mishirt_movil.view.ProfileScreen
import com.example.mishirt_movil.view.SettingsScreen
import com.example.mishirt_movil.viewmodel.CatalogViewModel
import com.example.mishirt_movil.viewmodel.HomeViewModel
import com.example.mishirt_movil.viewmodel.SettingsViewModel
import com.example.mishirt_movil.viewmodel.UserViewModel
import android.graphics.Color as AndroidColor
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            val userVm: UserViewModel = viewModel()
            val userState = userVm.uiState.collectAsState().value

            val settingsVm: SettingsViewModel = viewModel()
            val settingsState = settingsVm.uiState.collectAsState().value

            Mishirt_movilTheme(darkTheme = settingsState.isDarkTheme) {
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        when (currentRoute) {

                            "home" -> {
                                AppTopBar(
                                    onTitleClick = {
                                        navController.navigate("home") {
                                            popUpTo("home") { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    },
                                    onCatalogClick = { navController.navigate("catalog") },
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

                            "auth" -> {
                                TopAppBar(
                                    title = { Text(text = "Crear cuenta", color = Color.White) },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = "Volver",
                                                tint = Color.White
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MossGreen
                                    )
                                )
                            }

                            "catalog" -> {
                                TopAppBar(
                                    title = { Text(text = "Catálogo", color = Color.White) },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = "Volver",
                                                tint = Color.White
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MossGreen
                                    )
                                )
                            }

                            "details/{productId}" -> {
                                TopAppBar(
                                    title = { Text(text = "Detalle", color = Color.White) },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowBack,
                                                contentDescription = "Volver",
                                                tint = Color.White
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MossGreen
                                    )
                                )
                            }

                            else -> {

                            }
                        }
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
                                onProductClick = { product ->
                                    navController.navigate("details/${product.id}")
                                },
                                onBannerClick = { productId ->
                                    navController.navigate("details/$productId")
                                }
                            )
                        }

                        composable("catalog") {
                            val catalogVm: CatalogViewModel = viewModel()
                            val products = catalogVm.products.collectAsState().value

                            CatalogScreen(
                                products = products,
                                onProductClick = { product ->
                                    navController.navigate("details/${product.id}")
                                }
                            )
                        }

                        composable("details/{productId}") { entry ->
                            val productId = entry.arguments?.getString("productId")
                            val product = productId?.let { ProductsRepository.getById(it) }

                            if (product != null) {
                                ProductDetailScreen(product = product)
                            } else {
                                Text(
                                    text = "Producto no encontrado",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
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
