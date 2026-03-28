package com.emily.prayerpro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emily.prayerpro.ui.screen.AzkarScreen
import com.emily.prayerpro.ui.screen.PrayerScreen
import com.emily.prayerpro.ui.screen.SettingsScreen

sealed class Screen(val route: String) {
    object Prayer : Screen("prayer")
    object Azkar : Screen("azkar/{category}") {
        fun createRoute(category: String) = "azkar/$category"
    }
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Prayer.route
    ) {
        composable(Screen.Prayer.route) {
            PrayerScreen(
                onNavigateToAzkar = { cat -> navController.navigate(Screen.Azkar.createRoute(cat)) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Azkar.route) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "sabah"
            AzkarScreen(
                category = category,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}