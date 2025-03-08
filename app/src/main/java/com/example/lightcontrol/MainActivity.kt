package com.example.lightcontrol

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lightcontrol.ble.BleViewModel
import com.example.lightcontrol.components.LateralMenu
import com.example.lightcontrol.navigation.NavManager
import com.example.lightcontrol.ui.theme.LightControlTheme

/**
 * Main Activity for the Light Control application.
 *
 * Handles permissions and sets up the Compose content.
 */
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionsGranted = permissions.entries.all { it.value }
        if (!allPermissionsGranted) {
            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()

        setContent {
            LightControlTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val bleViewModel: BleViewModel = viewModel()
                    MainViewContent(bleViewModel)
                }
            }
        }
    }

    /**
     * Checks and requests necessary permissions.
     */
    private fun checkAndRequestPermissions() {
        val neededPermissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )

        val permissionsToRequest = neededPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest)
        }
    }
}

/**
 * Main view content setup with navigation and drawer.
 *
 * @param viewModel The BLE ViewModel.
 */
@Composable
fun MainViewContent(viewModel: BleViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LateralMenu(
        navController = navController,
        drawerState = drawerState
    ) {
        NavManager(navController, viewModel, drawerState)
    }
}
