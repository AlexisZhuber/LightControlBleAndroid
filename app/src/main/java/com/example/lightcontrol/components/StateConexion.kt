package com.example.lightcontrol.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.lightcontrol.R

/**
 * Composable function to display connection state.
 *
 * @param state True if connected, false otherwise.
 */
@Composable
fun StateConexion(state: Boolean) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (state) {
                Image(
                    painter = painterResource(id = R.drawable.connected),
                    contentDescription = "Connected",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text("Bluetooth connection successful", textAlign = TextAlign.Center)
            } else {
                Image(
                    painter = painterResource(id = R.drawable.unplugged),
                    contentDescription = "Not Connected",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text("Bluetooth is not connected", textAlign = TextAlign.Center)
            }
        }
    }
}
