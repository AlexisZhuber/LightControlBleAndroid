package com.example.lightcontrol.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lightcontrol.ble.BleViewModel
import com.example.lightcontrol.components.ColorPickerBle
import com.example.lightcontrol.components.MainTopBar
import com.example.lightcontrol.components.StateConexion

/**
 * Composable function for the Color Picker view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param drawerState The state of the navigation drawer.
 */
@Composable
fun ColorPickerView(bleViewModel: BleViewModel, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            MainTopBar(title = "COLOR PICKER", onClickBackButton = { /*TODO*/ }, drawerState = drawerState)
        }
    ) {
        ContentColorPickerView(bleViewModel, it)
    }
}

/**
 * Content for the Color Picker view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param paddingValues Padding values from the Scaffold.
 */
@Composable
fun ContentColorPickerView(bleViewModel: BleViewModel, paddingValues: PaddingValues) {
    val isConnected by bleViewModel.isConnected.collectAsState()

    if (isConnected) {
        bleViewModel.sendValues("!")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isConnected) {
                ColorPickerBle(bleViewModel)
            } else {
                StateConexion(false)
            }
        }
    }
}
