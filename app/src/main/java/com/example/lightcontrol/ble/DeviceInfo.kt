package com.example.lightcontrol.ble

/**
 * Data class representing device information.
 *
 * @property name The name of the device.
 * @property address The MAC address of the device.
 */
data class DeviceInfo(
    val name: String?,
    val address: String
)
