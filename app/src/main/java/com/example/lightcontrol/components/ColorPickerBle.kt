package com.example.lightcontrol.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lightcontrol.ble.BleViewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

/**
 * Composable function for a color picker with BLE integration.
 *
 * @param bleViewModel The BLE ViewModel for sending color values.
 */
@Composable
fun ColorPickerBle(bleViewModel: BleViewModel) {
    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = {
                // Converts the hexadecimal code to a formatted string
                val formattedString = hexToFormattedString(it.hexCode)
                println(formattedString)
                // Sends the formatted string via BLE
                bleViewModel.sendValues(formattedString)
            }
        )
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller
        )
    }
}

/**
 * Converts a hexadecimal color code to a formatted string.
 *
 * Assumes the hexadecimal code is in RRGGBB or RRGGBBAA format.
 *
 * @param hexCode The hexadecimal color code.
 * @return A formatted string representing the decimal values of the color.
 */
fun hexToFormattedString(hexCode: String): String {
    val decimalValues = hexCode.chunked(2).map { it.toInt(16) }
    return "*" + decimalValues.joinToString(separator = ",") + "."
}
