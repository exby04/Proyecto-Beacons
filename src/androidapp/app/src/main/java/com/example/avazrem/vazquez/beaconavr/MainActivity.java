package com.example.avazrem.vazquez.beaconavr;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private static final String MI_BEACON_NOMBRE = "AVRbeacon";

    private BluetoothLeScanner elEscanner;
    private ScanCallback callbackDelEscaneo = null;

    // --------------------------------------------------------------
    // Buscar TODOS los dispositivos
    // --------------------------------------------------------------
    private void buscarTodosLosDispositivosBTLE() {
        Log.d(ETIQUETA_LOG, "buscarTodosLosDispositivosBTLE(): empieza");

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);
                BluetoothDevice dispositivo = resultado.getDevice();
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String nombre = dispositivo.getName();
                int rssi = resultado.getRssi();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Log.d(ETIQUETA_LOG, "Dispositivo detectado: " + nombre + " | " + dispositivo.getAddress() + " | RSSI = " + rssi);
            }

            @Override
            public void onScanFailed(int errorCode) {
                Log.d(ETIQUETA_LOG, "buscarTodosLosDispositivosBTLE(): onScanFailed() código " + errorCode);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        elEscanner.startScan(this.callbackDelEscaneo);
        Log.d(ETIQUETA_LOG, "buscarTodosLosDispositivosBTLE(): escaneo iniciado (sin filtros)");
    }

    // --------------------------------------------------------------
    // Buscar SOLO nuestro beacon AVRbeacon
    // --------------------------------------------------------------
    private void buscarMisDispositivosBTLE() {
        Log.d(ETIQUETA_LOG, "buscarMisDispositivosBTLE(): empieza");

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                super.onScanResult(callbackType, resultado);

                BluetoothDevice bluetoothDevice = resultado.getDevice();
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String nombre = bluetoothDevice.getName();
                int rssi = resultado.getRssi();

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (nombre == null || !MI_BEACON_NOMBRE.equals(nombre)) {
                    // ignoramos dispositivos que no sean el nuestro
                    return;
                }

                byte[] bytes = resultado.getScanRecord().getBytes();
                TramaIBeacon tib = new TramaIBeacon(bytes);
                BeaconMedicion medicion = new BeaconMedicion(
                        tib,
                        bluetoothDevice.getAddress(),
                        rssi
                );

                Log.d(ETIQUETA_LOG, "****************************************************");
                Log.d(ETIQUETA_LOG, "****** BEACON PROPIO DETECTADO *********************");
                Log.d(ETIQUETA_LOG, "****************************************************");
                Log.d(ETIQUETA_LOG, "nombre = " + nombre);
                Log.d(ETIQUETA_LOG, "dirección = " + bluetoothDevice.getAddress());
                Log.d(ETIQUETA_LOG, "rssi = " + rssi);
                Log.d(ETIQUETA_LOG, "Medición interpretada: " + medicion.descripcion());
                Log.d(ETIQUETA_LOG, "****************************************************");
            }

            @Override
            public void onScanFailed(int errorCode) {
                Log.d(ETIQUETA_LOG, "buscarMisDispositivosBTLE(): onScanFailed() código " + errorCode);
            }
        };

        ScanFilter filtro = new ScanFilter.Builder()
                .setDeviceName(MI_BEACON_NOMBRE)
                .build();

        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        elEscanner.startScan(Collections.singletonList(filtro), settings, this.callbackDelEscaneo);
        Log.d(ETIQUETA_LOG, "buscarMisDispositivosBTLE(): escaneo iniciado SOLO para " + MI_BEACON_NOMBRE);
    }

    // --------------------------------------------------------------
    // Detener búsqueda
    // --------------------------------------------------------------
    private void detenerBusquedaDispositivosBTLE() {
        if (this.callbackDelEscaneo == null) return;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) return;

        elEscanner.stopScan(this.callbackDelEscaneo);
        this.callbackDelEscaneo = null;

        Log.d(ETIQUETA_LOG, "detenerBusquedaDispositivosBTLE(): escaneo detenido");
    }

    // --------------------------------------------------------------
    // Métodos llamados desde los botones XML
    // --------------------------------------------------------------
    public void botonBuscarDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, "Botón 'Buscar Dispositivos' pulsado");
        buscarTodosLosDispositivosBTLE();
    }

    public void botonBuscarNuestroDispositivoBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, "Botón 'Buscar Mi Dispositivo' pulsado");
        buscarMisDispositivosBTLE();
    }

    public void botonDetenerBusquedaDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, "Botón 'Detener Búsqueda' pulsado");
        detenerBusquedaDispositivosBTLE();
    }

    // --------------------------------------------------------------
    // Inicializar Bluetooth y permisos
    // --------------------------------------------------------------
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, "inicializarBlueTooth(): obtenemos adaptador BT");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        if (bta == null) {
            Log.d(ETIQUETA_LOG, "ERROR: No se pudo obtener el adaptador Bluetooth");
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) return;
        bta.enable();

        this.elEscanner = bta.getBluetoothLeScanner();
        if (this.elEscanner == null) {
            Log.d(ETIQUETA_LOG, "ERROR: No se pudo obtener el escáner BTLE");
        } else {
            Log.d(ETIQUETA_LOG, "inicializarBlueTooth(): escáner BTLE OK");
        }
    }

    private void pedirPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT},
                        CODIGO_PETICION_PERMISOS);
            }
        } else { // Android 11 o menor
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_PETICION_PERMISOS);
            }
        }
    }

    // --------------------------------------------------------------
    // Ciclo de vida
    // --------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(ETIQUETA_LOG, "onCreate(): empieza");

        pedirPermisos();
        inicializarBlueTooth();

        Log.d(ETIQUETA_LOG, "onCreate(): termina");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PETICION_PERMISOS && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            inicializarBlueTooth();
        }
    }
}
