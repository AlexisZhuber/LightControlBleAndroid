package com.example.lightcontrol.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lightcontrol.ble.DeviceInfo
import com.example.lightcontrol.ui.theme.cardColor
import com.example.lightcontrol.ui.theme.colorButton

/**
 * Composable function representing a device item.
 *
 * Displays device information and a connect button.
 *
 * @param deviceInfo The device information.
 * @param onDeviceClicked Callback when the device is clicked.
 */
@Composable
fun DeviceItem(deviceInfo: DeviceInfo, onDeviceClicked: (DeviceInfo) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = deviceInfo.name ?: "Unknown Device",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Address: ${deviceInfo.address}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
            Button(
                onClick = { onDeviceClicked(deviceInfo) },
                modifier = Modifier.align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorButton,
                    contentColor = Color.White
                )
            ) {
                Text("Connect")
            }
        }
    }
}
