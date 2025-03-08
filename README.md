# LightControl BLE Android

¡Bienvenido(a) a **LightControl BLE Android**! Este proyecto es una aplicación para Android que permite **escanear**, **conectar** y **controlar** dispositivos Bluetooth Low Energy (BLE) con un enfoque especial en el envío de colores a través de un **color picker** y una **interfaz de matriz**.

## Características Principales

- **Escaneo de dispositivos BLE**: Encuentra dispositivos cercanos y muestra la información (nombre y dirección MAC).
- **Conexión BLE estable**: Maneja la conexión y reconexión para asegurar una comunicación confiable.
- **Envío de datos**: Transmite valores a un dispositivo BLE, por ejemplo, códigos de color (RGB) o patrones para una matriz de LEDs.
- **Color Picker**: Selecciona colores mediante un panel HSV, barras de brillo y transparencia.
- **Matrix View**: Envía colores a celdas individuales en una matriz LED, permitiendo crear diseños personalizados.
- **Compatibilidad**: Funciona en Android 12 y versiones superiores (incluyendo Android 15).

## Requisitos

- **Android Studio** (Arctic Fox o superior).
- **SDK de Android** configurado (al menos la versión 31 para Android 12).
- **Dispositivo o emulador** con soporte BLE.  
  *Nota*: Para pruebas reales, se recomienda un dispositivo físico con Bluetooth habilitado.

## Configuración del Proyecto

1. **Clona este repositorio**:
   ```bash
   git clone https://github.com/Usuario/LightControlBleAndroid.git
   ```
2. **Abre la carpeta** en Android Studio.
3. **Verifica los permisos** en el archivo `AndroidManifest.xml` y el código de solicitud de permisos en `MainActivity`.  
   - Se necesitan permisos de ubicación y BLE para escanear y conectar en Android 12+.

## Uso

1. **Concede los permisos** necesarios cuando la app los solicite (BLE y ubicación).
2. **Activa** el Bluetooth y, en caso de Android 12+, los servicios de ubicación.
3. **Escanea** dispositivos BLE presionando el botón "Scan Devices".
4. **Selecciona** el dispositivo “SmartBleDevice” (o el que corresponda) para conectar.
5. **Explora** las vistas:
   - **Color Picker**: Elige colores y envíalos al dispositivo BLE.
   - **Matrix View**: Selecciona celdas para cambiar de color y crea patrones.

## Arquitectura

- **MVVM (Model-View-ViewModel)**:  
  - **BleViewModel** gestiona la lógica de conexión y escaneo BLE.  
  - **BleManager** interactúa directamente con la API de Bluetooth y expone flujos (StateFlow) para la UI.
- **Jetpack Compose**: Interfaz de usuario declarativa y reactiva.
- **Kotlin Coroutines**: Manejo de tareas asíncronas y reactividad de datos.

## Contribución

¡Las contribuciones son bienvenidas! Para contribuir:

1. **Fork** este repositorio.
2. Crea una rama con tus cambios:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. **Commitea** tus modificaciones:
   ```bash
   git commit -m "Implementación de nueva funcionalidad"
   ```
4. **Haz push** de tu rama y crea un **Pull Request**:
   ```bash
   git push -u origin feature/nueva-funcionalidad
   ```

## Licencia

Este proyecto está disponible bajo la licencia [MIT](./LICENSE). Puedes usarlo libremente para proyectos personales y comerciales.

## Contacto

Para dudas o sugerencias, puedes crear un [Issue](https://github.com/Usuario/LightControlBleAndroid/issues) o contactar al autor:
- **Autor**: [Tu Nombre](https://github.com/Usuario)  
- **Email**: tunombre@example.com

¡Gracias por visitar **LightControl BLE Android**! Esperamos que disfrutes creando efectos de iluminación con BLE. ¡Diviértete programando!
