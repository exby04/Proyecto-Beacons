package com.example.avazrem.vazquez.beaconavr;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogicaFake {
    private final ApiService api;

    public LogicaFake(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // ej: "http://10.0.2.2:8080/api/"
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);
    }

    // POST -> enviar medición
    public void guardarMedicion(BeaconMedicion medicion) {
        api.guardarMedicion(medicion).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("LogicaFake", "Medición guardada en servidor");
                } else {
                    Log.e("LogicaFake", "Error HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("LogicaFake", "Error de conexión", t);
            }
        });
    }

    // GET -> todas las mediciones
    public void recuperarMediciones() {
        api.recuperarMediciones().enqueue(new Callback<List<BeaconMedicion>>() {
            @Override
            public void onResponse(Call<List<BeaconMedicion>> call, Response<List<BeaconMedicion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (BeaconMedicion m : response.body()) {
                        Log.d("LogicaFake", "Medición: " + m.descripcion());
                    }
                } else {
                    Log.e("LogicaFake", "Error HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<BeaconMedicion>> call, Throwable t) {
                Log.e("LogicaFake", "Error de conexión", t);
            }
        });
    }

    // GET -> mediciones por MAC
    public void recuperarMedicionesPorMac(String mac) {
        api.recuperarMedicionesPorMac(mac).enqueue(new Callback<List<BeaconMedicion>>() {
            @Override
            public void onResponse(Call<List<BeaconMedicion>> call, Response<List<BeaconMedicion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LogicaFake", "Recibidas " + response.body().size() + " mediciones de " + mac);
                } else {
                    Log.e("LogicaFake", "Error HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<BeaconMedicion>> call, Throwable t) {
                Log.e("LogicaFake", "Error de conexión", t);
            }
        });
    }

    // GET -> dispositivos
    public void recuperarDispositivos() {
        api.recuperarDispositivos().enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LogicaFake", "Dispositivos recibidos: " + response.body().size());
                } else {
                    Log.e("LogicaFake", "Error HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Log.e("LogicaFake", "Error de conexión", t);
            }
        });
    }
}
