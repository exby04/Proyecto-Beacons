package com.example.avazrem.vazquez.beaconavr;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogicaFake {
    private final ApiService api;

    public LogicaFake(String baseUrl) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // ej: "http://192.168.84.97:8080/api/"
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(ApiService.class);
    }

    // POST -> enviar medición
    public void guardarMedicion(BeaconMedicion medicion) {
        Gson gsonDebug = new GsonBuilder().setPrettyPrinting().create();
        Log.d("LogicaFake", "📤 JSON ENVIADO:\n" + gsonDebug.toJson(medicion));

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

}
