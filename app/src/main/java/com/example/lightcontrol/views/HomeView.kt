package com.example.lightcontrol.views

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.lightcontrol.ble.BleViewModel
import com.example.lightcontrol.components.DeviceItem
import com.example.lightcontrol.components.MainTopBar
import com.example.lightcontrol.components.StateConexion
import com.example.lightcontrol.components.StaticSectionTitle
import com.example.lightcontrol.ui.theme.colorButton
import com.example.lightcontrol.ui.theme.colorDisconnect

/**
 * Composable function for the Home view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param drawerState The state of the navigation drawer.
 */
@Composable
fun HomeView(bleViewModel: BleViewModel, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            MainTopBar(title = "BLE CONNECT", onClickBackButton = { /*TODO*/ }, drawerState = drawerState)
        }
    ) {
        ContentHomeView(bleViewModel, it)
    }
}

/**
 * Content for the Home view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param paddingValues Padding values from the Scaffold.
 */
@Composable
fun ContentHomeView(bleViewModel: BleViewModel, paddingValues: PaddingValues) {
    val scanResults by bleViewModel.scanResults.collectAsState()
    val isConnected by bleViewModel.isConnected.collectAsState()
    val context = LocalContext.current
    val showBluetoothDialog = remember { mutableStateOf(false) }

    if (isConnected) {
        bleViewModel.sendValues("!")
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                if (!isConnected) {
                    // Check if Bluetooth is enabled
                    if (bleViewModel.isBluetoothEnabled()) {
                        bleViewModel.startScan()
                    } else {
                        // Show dialog to ask user to enable Bluetooth
                        showBluetoothDialog.value = true
                    }
                } else {
                    bleViewModel.disconnect()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isConnected) colorButton else colorDisconnect,
            )
        ) {
            StaticSectionTitle(if (!isConnected) "Scan Devices" else "Disconnect")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (scanResults.isEmpty() && !isConnected) {
            Spacer(modifier = Modifier.height(200.dp))
            StateConexion(false)
        }

        if (isConnected) {
            Spacer(modifier = Modifier.height(200.dp))
            StateConexion(true)
        }

        if (!isConnected) {
            // Filter scan results to include only devices with the name "SmartBleDevice"
            val filteredResults = scanResults.filter { it.name == "SmartBleDevice" }
            LazyColumn {
                items(filteredResults, key = { it.address }) { deviceInfo ->
                    DeviceItem(deviceInfo = deviceInfo) {
                        bleViewModel.connectToDevice(it.address)
                    }
                }
            }
        }

        if (showBluetoothDialog.value) {
            AlertDialog(
                onDismissRequest = { showBluetoothDialog.value = false },
                title = { Text("Bluetooth Required") },
                text = { Text("Please turn on Bluetooth to scan for devices.") },
                confirmButton = {
                    Button(
                        onClick = {
                            // Attempt to enable Bluetooth via an Intent
                            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                            ContextCompat.startActivity(context, enableBtIntent, null)
                            showBluetoothDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorButton,
                        )
                    ) {
                        Text("Enable Bluetooth")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showBluetoothDialog.value = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                        )
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
