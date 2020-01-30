package com.blogspot.soyamr.weather;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResults = findViewById(R.id.results_from_api);

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Location.class, new MyDeserializer<Location>("location"))
//                .registerTypeAdapter(Current.class, new MyDeserializer<Current>("current"))
//                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherstackApi weatherstackApi = retrofit.create(WeatherstackApi.class);
        Call<MainDataFromJson> call = weatherstackApi.getInfo();

        call.enqueue(new Callback<MainDataFromJson>() {
            @Override
            public void onResponse(Call<MainDataFromJson> call, Response<MainDataFromJson> response) {
                if (!response.isSuccessful()) {
                    textResults.setText("code: " + response.code());
                    return;
                }

                MainDataFromJson cityWeathers = response.body();
                Location location = cityWeathers.getLocation();
                Current current= cityWeathers.getCurrent();
                textResults.setText(location.getTimezone_id() + " ");
                textResults.append(location.getCityName() + "\n");
                textResults.append(location.getCountryName() + "\n");

            }

            @Override
            public void onFailure(Call<MainDataFromJson> call, Throwable t) {
                textResults.setText(t.getMessage());
            }
        });
    }
}
