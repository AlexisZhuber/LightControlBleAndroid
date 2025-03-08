# LightControl BLE Android

Welcome to **LightControl BLE Android**! This project is an Android application designed to **scan**, **connect**, and **control** Bluetooth Low Energy (BLE) devices, with a special focus on sending colors through a **color picker** and a **matrix** interface.

## Key Features

- **BLE Device Scanning**: Discover nearby devices and display their information (name and MAC address).
- **Stable BLE Connection**: Manages connection and reconnection for reliable communication.
- **Data Transmission**: Sends values to a BLE device, such as color codes (RGB) or patterns for an LED matrix.
- **Color Picker**: Select colors using an HSV panel, brightness, and transparency sliders.
- **Matrix View**: Send colors to individual cells in an LED matrix, allowing for custom patterns.
- **Compatibility**: Works on Android 12 and above (including Android 15).

## Requirements

- **Android Studio** (Arctic Fox or newer).
- **Android SDK** configured (at least version 31 for Android 12).
- **Device or Emulator** with BLE support.  
  *Note*: For real testing, a physical device with Bluetooth enabled is recommended.

## Project Setup

1. **Clone this repository**:
   ```bash
   git clone https://github.com/Username/LightControlBleAndroid.git
   ```
2. **Open** the folder in Android Studio.
3. **Check permissions** in the `AndroidManifest.xml` file and in `MainActivity`:
   - Location and BLE permissions are required to scan and connect on Android 12+.

## Usage

1. **Grant permissions** when prompted (BLE and location).
2. **Enable** Bluetooth and, for Android 12+, location services.
3. **Scan** for BLE devices by tapping the "Scan Devices" button.
4. **Select** your “SmartBleDevice” (or relevant device) to connect.
5. **Explore** the views:
   - **Color Picker**: Choose colors and send them to the BLE device.
   - **Matrix View**: Select cells to change colors and create patterns.

## Architecture

- **MVVM (Model-View-ViewModel)**:  
  - **BleViewModel** manages BLE logic for scanning and connecting.  
  - **BleManager** directly interfaces with the Bluetooth API and exposes StateFlows for the UI.
- **Jetpack Compose**: Declarative and reactive user interface.
- **Kotlin Coroutines**: Asynchronous tasks and reactive data handling.

## Contributing

Contributions are welcome! To contribute:

1. **Fork** this repository.
2. Create a new branch for your changes:
   ```bash
   git checkout -b feature/new-feature
   ```
3. **Commit** your modifications:
   ```bash
   git commit -m "Implement new feature"
   ```
4. **Push** your branch and open a **Pull Request**:
   ```bash
   git push -u origin feature/new-feature
   ```

## License

This project is available under the [MIT License](./LICENSE). Feel free to use it for both personal and commercial projects.

## Contact

For questions or suggestions, create an [Issue](https://github.com/AlexisZhuber/LightControlBleAndroid/issues) or reach out to the author:
- **Author**: [Alexis Mora](https://github.com/AlexisMora)  
- **Email**: alexismora602@gmail.com

Thank you for checking out **LightControl BLE Android**! We hope you enjoy creating lighting effects with BLE. Happy coding!
