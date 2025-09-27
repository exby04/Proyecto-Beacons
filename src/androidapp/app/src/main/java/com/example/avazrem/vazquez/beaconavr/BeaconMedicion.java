package com.example.avazrem.vazquez.beaconavr;

public class BeaconMedicion {
    public static final int SENSOR_CO2 = 11;
    public static final int SENSOR_TEMP = 12;
    public static final int SENSOR_RUIDO = 13;

    private final int sensorId;
    private final int contador;
    private final int valor;

    public BeaconMedicion(TramaIBeacon tib) {
        int major = Utilidades.bytesToInt(tib.getMajor());
        this.sensorId = (major >> 8) & 0xFF;  // byte alto
        this.contador = major & 0xFF;         // byte bajo
        this.valor = Utilidades.bytesToInt(tib.getMinor());
    }

    public int getSensorId() {
        return sensorId;
    }

    public int getContador() {
        return contador;
    }

    public int getValor() {
        return valor;
    }

    public String descripcion() {
        switch (sensorId) {
            case SENSOR_CO2:
                return "CO2 = " + valor + " ppm (c=" + contador + ")";
            case SENSOR_TEMP:
                return "Temperatura = " + valor + " °C (c=" + contador + ")";
            case SENSOR_RUIDO:
                return "Ruido = " + valor + " dB (c=" + contador + ")";
            default:
                return "Sensor " + sensorId + " valor=" + valor + " (c=" + contador + ")";
        }
    }
}