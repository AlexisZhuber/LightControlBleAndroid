package com.example.lightcontrol.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * BLE Manager to handle Bluetooth Low Energy operations.
 *
 * This class manages scanning, connecting, and sending values via BLE.
 */
class BleManager(private val context: Context) {

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothGatt: BluetoothGatt? = null
    private val _scanResults = MutableStateFlow<List<DeviceInfo>>(emptyList())
    val scanResults = _scanResults.asStateFlow()
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private var scanningCallback: ScanCallback? = null

    /**
     * Starts BLE scanning.
     */
    @SuppressLint("MissingPermission")
    fun startScan() {
        bluetoothAdapter?.bluetoothLeScanner?.let { scanner ->
            val callback = object : ScanCallback() {
                override fun onScanResult(callbackType: Int, result: ScanResult) {
                    super.onScanResult(callbackType, result)
                    val device = result.device
                    val deviceInfo = DeviceInfo(name = device.name ?: "Unknown Device", address = device.address)
                    val updatedList = _scanResults.value.toMutableList()
                    if (!updatedList.any { it.address == deviceInfo.address }) {
                        updatedList.add(deviceInfo)
                        _scanResults.value = updatedList
                    }
                }
            }
            scanningCallback = callback
            scanner.startScan(callback)
        }
    }

    /**
     * Stops BLE scanning.
     */
    @SuppressLint("MissingPermission")
    fun stopScan() {
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanningCallback)
        scanningCallback = null
    }

    /**
     * Connects to a BLE device with the given address.
     *
     * For Android 12 devices (API 31) uses autoConnect = true to improve stability.
     *
     * @param address The MAC address of the device.
     * @return True if connection is initiated, false otherwise.
     */
    @SuppressLint("MissingPermission")
    fun connectToDevice(address: String): Boolean {
        val device = bluetoothAdapter?.getRemoteDevice(address)
        // Para Android 12 (API 31) se usa autoConnect = true; para otros se usa false.
        val autoConnect = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S) true else false
        bluetoothGatt = device?.connectGatt(context, autoConnect, gattCallback)
        return bluetoothGatt != null
    }

    /**
     * Sends a string value to the connected BLE device.
     *
     * @param valueToSend The string value to send.
     */
    @SuppressLint("MissingPermission")
    fun sendValues(valueToSend: String) {
        val service = bluetoothGatt?.getService(UUID.fromString(SERVICE_UUID))
        val characteristic = service?.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID))
        characteristic?.let {
            // Convert the string to a ByteArray and assign it to the characteristic
            it.value = valueToSend.toByteArray(Charsets.UTF_8)
            bluetoothGatt?.writeCharacteristic(it)
        }
    }

    /**
     * GATT callback to handle connection events.
     */
    @SuppressLint("MissingPermission")
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    // Start service discovery and wait for onServicesDiscovered
                    gatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    bluetoothGatt?.close()
                    bluetoothGatt = null
                    _isConnected.value = false
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // Only update connection state once services are discovered successfully
                _isConnected.value = true
            } else {
                _isConnected.value = false
            }
        }
    }

    /**
     * Checks if Bluetooth is enabled.
     *
     * @return True if Bluetooth is enabled, false otherwise.
     */
    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    /**
     * Disconnects from the BLE device.
     */
    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
    }

    companion object {
        // UUID for the BLE service
        const val SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
        // UUID for the BLE characteristic
        const val CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8"
    }
}
