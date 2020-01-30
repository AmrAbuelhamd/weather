package com.blogspot.soyamr.weather;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.soyamr.weather.model.Current;
import com.blogspot.soyamr.weather.model.Location;
import com.blogspot.soyamr.weather.model.JsonResponse;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherstackApi weatherstackApi = retrofit.create(WeatherstackApi.class);
        Call<JsonResponse> call = weatherstackApi.getInfo("tomsk");

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (!response.isSuccessful()) {
                    textResults.setText("code: " + response.code());
                    return;
                }

                JsonResponse cityWeathers = response.body();
                Location location = cityWeathers.getLocation();
                Current current= cityWeathers.getCurrent();
                textResults.setText(location.getTimezone_id() + " ");
                textResults.append(location.getCityName() + "\n");
                textResults.append(location.getCountryName() + "\n");
                textResults.append(current.getHumidity() + "\n");
                textResults.append(current.getFeelslike() + "\n");

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                textResults.setText(t.getMessage());
            }
        });
    }
}
