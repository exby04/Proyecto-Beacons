package com.example.avazrem.vazquez.beaconavr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @POST("guardarMediciones")
    Call<Void> guardarMedicion(@Body BeaconMedicion medicion);
}
