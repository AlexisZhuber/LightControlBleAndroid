package com.example.lightcontrol.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lightcontrol.ble.BleViewModel
import com.example.lightcontrol.views.ColorPickerView
import com.example.lightcontrol.views.ContactView
import com.example.lightcontrol.views.HomeView
import com.example.lightcontrol.views.MatrixView
import androidx.navigation.NavController as NavController1

/**
 * Composable function managing navigation.
 *
 * @param navController The navigation controller.
 * @param viewModel The BLE ViewModel.
 * @param drawerState The state of the navigation drawer.
 */
@Composable
fun NavManager(
    navController: NavController1,
    viewModel: BleViewModel,
    drawerState: DrawerState
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "Home"
    ) {
        composable("Home") {
            HomeView(viewModel, drawerState)
        }
        composable("ColorPicker") {
            ColorPickerView(viewModel, drawerState)
        }
        composable("Matrix") {
            MatrixView(viewModel, drawerState)
        }
        composable("Contact") {
            ContactView(drawerState)
        }
        /*
        composable("DetailView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.StringType },
        )){
            val id = it.arguments?.getString("id") ?: ""
            DetailView(viewModel, navController, id, drawerState)
        }
        */
    }
}
