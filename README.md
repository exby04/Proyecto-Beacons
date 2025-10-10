# Proyecto Biometría Ambiental – 2025

Este repositorio forma parte del proyecto **Biometría y Medio Ambiente 2025**, centrado en el **registro y análisis de parámetros ambientales** mediante dispositivos **BLE iBeacon**, una **aplicación Android** para detección, y un **servidor REST** que almacena las mediciones en una base de datos MySQL.

---

## Estructura del Proyecto

### Arduino / C++ (Emisor BLE)
Código encargado de generar tramas iBeacon y simular sensores ambientales.

**Archivos principales:**
- `HolaMundoIBeacon.ino` – Sketch principal del emisor.  
- `LED.h` – Control del LED integrado.  
- `PuertoSerie.h` – Comunicación serie para depuración.  
- `EmisoraBLE.h` – Configuración y emisión de tramas BLE.  
- `ServicioEnEmisora.h` – Definición de servicios BLE personalizados.  
- `Publicador.h` – Publicación periódica de valores ambientales.  
- `Medidor.h` – Generación de lecturas simuladas (CO₂ y temperatura).  

---

### Android / Java (Receptor BLE)
Aplicación móvil para el escaneo de beacons, decodificación de tramas y envío de datos al backend.

**Archivos destacados:**
- `MainActivity.java` – Control principal del escaneo BLE.  
- `TramaIBeacon.java` – Decodificación de las tramas iBeacon.  
- `Utilidades.java` – Funciones auxiliares para procesamiento.  

---

### Servidor REST (Node.js + Express)
API REST encargada de recibir, validar y almacenar las mediciones en MySQL.

**Endpoints principales:**
- `GET /api/recuperarMediciones?limit=n` → Recupera las últimas *n* mediciones.  
- `POST /api/guardarMediciones` → Inserta una nueva medición.

---

## Tecnologías Utilizadas

- **C++ / Arduino IDE** – Emisión BLE y simulación de sensores.  
- **Adafruit Feather nRF52840 + Bluefruit Library** – Hardware BLE.  
- **Java / Android Studio** – Escaneo y envío de tramas BLE.  
- **Node.js + Express** – Backend REST.  
- **MySQL (Plesk)** – Persistencia de datos.  
- **HTML / PHP** – Visualización web.  
- **Figma / UML** – Diseño de interfaz y diagramas de arquitectura.  

---

## Cómo Ejecutar el Proyecto

### 1. Emisor (Arduino)
1. Abre `HolaMundoIBeacon.ino` en **Arduino IDE**.  
2. Selecciona la placa **Adafruit Feather nRF52840 Express**.  
3. Carga el sketch al dispositivo.  
4. El beacon comenzará a emitir datos ambientales simulados (CO₂ y temperatura).

### 2. Receptor (Android)
1. Abre el proyecto en **Android Studio**.  
2. Concede permisos de Bluetooth y ubicación.  
3. Ejecuta la aplicación y detecta el beacon **AVRbeacon**.  
4. Los datos decodificados se envían automáticamente al servidor REST.

### 3. Servidor (Node.js)
1. Instala las dependencias:
   ```bash
   npm install
2. Inicia el servidor
  node mainServidorREST.js

### 4. TEST (Node.js)
ir a la carpeta test dentro de servidor y hacer node test_mediciones.js

Diagramas y Diseño
Clases C++: LED, EmisoraBLE, Publicador, Medidor.
Diagrama BLE: Flujo de datos entre emisor y receptor.
Frontend PHP: Interfaz web para la consulta de mediciones.

Créditos
Proyecto desarrollado por Alejandro Vázquez Remes
Universitat Politècnica de València – Biometría y Medio Ambiente 2025
