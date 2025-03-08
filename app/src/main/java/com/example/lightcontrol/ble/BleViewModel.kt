package com.example.lightcontrol.ble

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for BLE operations.
 *
 * This ViewModel interacts with the BleManager to perform BLE scanning, connection, and communication.
 */
class BleViewModel(application: Application) : AndroidViewModel(application) {
    private val bleManager = BleManager(application)

    // State flow for scan results (list of DeviceInfo)
    private val _scanResults = MutableStateFlow<List<DeviceInfo>>(emptyList())
    val scanResults = _scanResults.asStateFlow()

    // State flow for connection status
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    init {
        // Subscribe to scan results from BleManager
        viewModelScope.launch {
            bleManager.scanResults.collect { devices ->
                _scanResults.value = devices
            }
        }

        // Subscribe to connection status from BleManager
        viewModelScope.launch {
            bleManager.isConnected.collect { connected ->
                _isConnected.value = connected
            }
        }
    }

    /**
     * Starts scanning for BLE devices.
     */
    fun startScan() {
        viewModelScope.launch {
            bleManager.startScan()
        }
    }

    /**
     * Connects to a BLE device with the given address.
     *
     * Stops scanning before attempting connection.
     *
     * @param address The MAC address of the device.
     */
    fun connectToDevice(address: String) {
        stopScan()  // Stop scanning before trying to connect

        viewModelScope.launch {
            val result = bleManager.connectToDevice(address)
            if (result) {
                clearScanResults()
            }
            _isConnected.value = result
        }
    }

    /**
     * Sends a string value to the connected BLE device.
     *
     * @param valueToSend The string value to send.
     */
    fun sendValues(valueToSend: String) {
        viewModelScope.launch {
            bleManager.sendValues(valueToSend)
        }
    }

    /**
     * Stops scanning for BLE devices.
     */
    fun stopScan() {
        viewModelScope.launch {
            bleManager.stopScan()
            // Optionally update state if needed to reflect that scanning has stopped
        }
    }

    /**
     * Clears the list of scanned devices.
     */
    fun clearScanResults() {
        _scanResults.value = emptyList()
    }

    /**
     * Checks if Bluetooth is enabled.
     *
     * @return True if Bluetooth is enabled, false otherwise.
     */
    fun isBluetoothEnabled(): Boolean {
        return bleManager.isBluetoothEnabled()
    }

    /**
     * Disconnects from the BLE device.
     */
    fun disconnect() {
        viewModelScope.launch {
            bleManager.disconnect()
            _isConnected.value = false
        }
    }
}
