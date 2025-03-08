package com.example.lightcontrol.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lightcontrol.ble.BleViewModel
import com.example.lightcontrol.components.ColorPicker
import com.example.lightcontrol.components.MainTopBar
import com.example.lightcontrol.components.StateConexion
import com.example.lightcontrol.components.StaticSectionTitle
import com.example.lightcontrol.ui.theme.colorButton
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

/**
 * Composable function for the Matrix view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param drawerState The state of the navigation drawer.
 */
@Composable
fun MatrixView(bleViewModel: BleViewModel, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            MainTopBar(title = "MATRIX", onClickBackButton = { /*TODO*/ }, drawerState = drawerState)
        }
    ) {
        ContentMatrixView(bleViewModel, it)
    }
}

/**
 * Content for the Matrix view.
 *
 * @param bleViewModel The BLE ViewModel.
 * @param paddingValues Padding values from the Scaffold.
 */
@Composable
fun ContentMatrixView(bleViewModel: BleViewModel, paddingValues: PaddingValues) {
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isConnected) {
                MatrixButtons(bleViewModel)
            } else {
                StateConexion(false)
            }
        }
    }
}

/**
 * Composable function for matrix buttons.
 *
 * @param bleViewModel The BLE ViewModel.
 */
@Composable
fun MatrixButtons(bleViewModel: BleViewModel) {
    val isConnected by bleViewModel.isConnected.collectAsState()
    val gridState = rememberLazyGridState()
    val buttonColors = remember { mutableStateListOf<Color>() }
    while (buttonColors.size < 64) buttonColors.add(Color.LightGray)

    val (selectedColor, setSelectedColor) = remember { mutableStateOf(Color.Unspecified) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val controller = rememberColorPickerController()

    if (buttonColors.size < 64) {
        repeat(64 - buttonColors.size) {
            buttonColors.add(Color.LightGray)
        }
    }

    if (showDialog) {
        ColorPickerDialog(
            controller = controller,
            onDismiss = { setShowDialog(false) },
            onColorSelected = { color ->
                setSelectedColor(color)
                setShowDialog(false)
            }
        )
    }

    Column {
        Button(
            onClick = { setShowDialog(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorButton,
            )
        ) {
            StaticSectionTitle("Select Color")
        }

        Spacer(modifier = Modifier.height(8.dp))

        AlphaTile(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(6.dp)),
            controller = controller
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            state = gridState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            items(64) { index ->
                Button(
                    onClick = {
                        if (selectedColor != Color.Unspecified) {
                            buttonColors[index] = selectedColor
                            if (isConnected) { // Only send if connected
                                if (selectedColor != Color.LightGray) {
                                    bleViewModel.sendValues(formatColorToString(index, selectedColor))
                                } else {
                                    bleViewModel.sendValues("_${index},0,0,0,0.")
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColors[index])
                ) { }
            }
        }

        Button(
            onClick = {
                bleViewModel.sendValues("!")
                val newList = buttonColors.map { Color.LightGray }.toMutableStateList()
                buttonColors.clear()
                buttonColors.addAll(newList)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorButton,
            )
        ) {
            StaticSectionTitle("Clear matrix")
        }
    }
}

/**
 * Composable function for the color picker dialog.
 *
 * @param controller The color picker controller.
 * @param onDismiss Callback when dialog is dismissed.
 * @param onColorSelected Callback when a color is selected.
 */
@Composable
fun ColorPickerDialog(
    controller: ColorPickerController,
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(DpSize(300.dp, 600.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ColorPicker(controller = controller)

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            // Send a default color to "clear" the color
                            onColorSelected(Color.LightGray)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Clear color")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onColorSelected(controller.selectedColor.value)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorButton,
                        )
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

/**
 * Formats the color and index into a string.
 *
 * @param index The index of the button.
 * @param color The selected color.
 * @return A formatted string with the index and color values.
 */
fun formatColorToString(index: Int, color: Color): String {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    return "_${index},0,${red},${green},${blue}."
}
