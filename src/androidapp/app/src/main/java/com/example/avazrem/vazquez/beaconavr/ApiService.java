package com.example.avazrem.vazquez.beaconavr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    @POST("guardarMediciones")
    Call<Void> guardarMedicion(@Body BeaconMedicion medicion);

    @GET("recuperarMediciones")
    Call<List<BeaconMedicion>> recuperarMediciones();

    @GET("recuperarMediciones/{mac}")
    Call<List<BeaconMedicion>> recuperarMedicionesPorMac(@Path("mac") String mac);

    @GET("dispositivos")
    Call<List<Object>> recuperarDispositivos();
}
