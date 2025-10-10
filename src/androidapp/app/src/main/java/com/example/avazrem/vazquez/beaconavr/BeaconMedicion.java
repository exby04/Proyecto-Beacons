package com.example.avazrem.vazquez.beaconavr;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class BeaconMedicion {

    public static final int SENSOR_CO2   = 11;
    public static final int SENSOR_TEMP  = 12;
    public static final int SENSOR_RUIDO = 13;

    // Campos que SÍ se envían
    @Expose @SerializedName("mac")
    private final String dispositivoMac;

    @Expose @SerializedName("sensorId")
    private final int sensorId;

    @Expose @SerializedName("valor")
    private final float valor;

    @Expose @SerializedName("rssi")
    private final int rssi;

    @Expose @SerializedName("fecha")
    private final long timestamp;

    @Expose @SerializedName("uuid")
    private final String uuid;

    // Campos internos (NO se envían)
    @Expose(serialize = false)
    private final int contador;

    @Expose(serialize = false)
    private final String nombre;

    // ----------------------------------------------------------------
    public BeaconMedicion(TramaIBeacon tib, String mac, int rssi) {
        int major = Utilidades.bytesToInt(tib.getMajor());
        int minor = Utilidades.bytesToInt(tib.getMinor());

        this.sensorId = (major >> 8) & 0xFF;   // byte alto = sensorId
        this.contador = major & 0xFF;          // byte bajo = contador
        this.valor = (short) minor;
        this.dispositivoMac = mac;
        this.rssi = rssi;
        this.timestamp = System.currentTimeMillis();
        this.nombre = "GTI";
        this.uuid = tib.getUUIDComoString();   // ahora devuelve el UUID real
    }

    // ----------------------------------------------------------------
    public int getContador() { return contador; }
    public String getUuid() { return uuid; }

    public String descripcion() {
        switch (sensorId) {
            case SENSOR_CO2:  return "CO2 = " + valor + " ppm (c=" + contador + ")";
            case SENSOR_TEMP: return "Temperatura = " + valor + " °C (c=" + contador + ")";
            case SENSOR_RUIDO:return "Ruido = " + valor + " dB (c=" + contador + ")";
            default:          return "Sensor " + sensorId + " valor=" + valor + " (c=" + contador + ")";
        }
    }
}
