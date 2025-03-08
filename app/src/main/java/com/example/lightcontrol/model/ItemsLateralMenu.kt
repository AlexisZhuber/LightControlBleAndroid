package com.example.lightcontrol.model

import com.example.lightcontrol.R

/**
 * Sealed class representing items in the lateral menu.
 *
 * @property icon Resource ID for the icon.
 * @property title Title of the menu item.
 * @property ruta Navigation route for the menu item.
 */
sealed class ItemsLateralMenu(
    val icon: Int,
    val title: String,
    val ruta: String
) {
    object ItemLateralMenu1 : ItemsLateralMenu(
        R.drawable.bluetooth,
        "Bluetooth connection",
        "Home"
    )
    object ItemLateralMenu2 : ItemsLateralMenu(
        R.drawable.picker,
        "Color Picker",
        "ColorPicker"
    )
    object ItemLateralMenu3 : ItemsLateralMenu(
        R.drawable.matrix,
        "Matrix",
        "Matrix"
    )
    object ItemLateralMenu4 : ItemsLateralMenu(
        R.drawable.operator,
        "Contact",
        "Contact"
    )
}
