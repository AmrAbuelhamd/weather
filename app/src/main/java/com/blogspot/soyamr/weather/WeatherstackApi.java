package com.blogspot.soyamr.weather;

import android.database.CursorIndexOutOfBoundsException;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherstackApi {

    @GET("/current?access_key=8c6096867f9cf9e48693108730a7c7fe&query=alexandria")
    Call<MainDataFromJson> getInfo();
}
