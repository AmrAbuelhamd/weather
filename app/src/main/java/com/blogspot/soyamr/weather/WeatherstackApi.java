package com.blogspot.soyamr.weather;

import com.blogspot.soyamr.weather.model.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherstackApi {

    @GET("/current?access_key=8c6096867f9cf9e48693108730a7c7fe")
    Call<JsonResponse> getInfo(@Query("query") String cityName);
}
