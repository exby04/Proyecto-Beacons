package com.example.avazrem.vazquez.beaconavr;

import com.google.gson.annotations.SerializedName;

public class BeaconMedicion {
    public static final int SENSOR_CO2   = 11;
    public static final int SENSOR_TEMP  = 12;
    public static final int SENSOR_RUIDO = 13;

    @SerializedName("mac")
    private final String dispositivoMac;   // se serializa como "mac"

    @SerializedName("sensorId")
    private final int sensorId;

    @SerializedName("contador")
    private final int contador;

    @SerializedName("valor")
    private final int valor;

    @SerializedName("rssi")
    private final int rssi;

    @SerializedName("fecha")
    private final long timestamp;          // se envía como milisegundos (el server lo convierte)

    @SerializedName("nombre")
    private final String nombre;           // si no lo usas, puedes quitarlo

    public BeaconMedicion(TramaIBeacon tib, String mac, int rssi) {
        int major = Utilidades.bytesToInt(tib.getMajor());   // big endian
        int minor = Utilidades.bytesToInt(tib.getMinor());

        this.sensorId = (major >> 8) & 0xFF;   // byte alto = sensorId
        this.contador = major & 0xFF;          // byte bajo = contador
        this.valor = (short) minor;            // soporta negativos
        this.dispositivoMac = mac;
        this.rssi = rssi;
        this.timestamp = System.currentTimeMillis();
        this.nombre = "GTI"; // el server usa "GTI" por defecto si no llega
    }

    public String getDispositivoMac() { return dispositivoMac; }
    public int getSensorId() { return sensorId; }
    public int getContador() { return contador; }
    public int getValor() { return valor; }
    public int getRssi() { return rssi; }
    public long getTimestamp() { return timestamp; }
    public String getNombre() { return nombre; }

    public String descripcion() {
        switch (sensorId) {
            case SENSOR_CO2:  return "CO2 = " + valor + " ppm (c=" + contador + ")";
            case SENSOR_TEMP: return "Temperatura = " + valor + " °C (c=" + contador + ")";
            case SENSOR_RUIDO:return "Ruido = " + valor + " dB (c=" + contador + ")";
            default:          return "Sensor " + sensorId + " valor=" + valor + " (c=" + contador + ")";
        }
    }
}
