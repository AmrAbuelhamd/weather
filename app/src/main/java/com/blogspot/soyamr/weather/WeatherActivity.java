package com.blogspot.soyamr.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.soyamr.weather.model.Current;
import com.blogspot.soyamr.weather.model.JsonResponse;
import com.blogspot.soyamr.weather.model.Location;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView temp;
    private TextView describtion;
    private TextView cityName;
    private TextView countnryName;
    private TextView percip;
    private TextView windSpeed;
    private TextView humidity;
    private TextView recievedTemp;
    private TextView visibiltiy;
    private TextView pressure;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    private TextView errorMessageTextView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-01-31 02:32:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        imageView = findViewById(R.id.imageView);
        temp = findViewById(R.id.temp);
        describtion = findViewById(R.id.describtion);
        cityName = findViewById(R.id.city_name);
        countnryName = findViewById(R.id.countnry_name);
        percip = findViewById(R.id.percip);
        windSpeed = findViewById(R.id.wind_speed);
        humidity = findViewById(R.id.humidity);
        recievedTemp = findViewById(R.id.recieved_temp);
        visibiltiy = findViewById(R.id.visibiltiy);
        pressure = findViewById(R.id.pressure);
        frameLayout = findViewById(R.id.progressBarHolder);
        progressBar = findViewById(R.id.progress_bar);
        errorMessageTextView = findViewById(R.id.error_message);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        Intent intent = getIntent();
        String cityNameForQuery = intent.getStringExtra("city name");

        findViews();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherstackApi weatherstackApi = retrofit.create(WeatherstackApi.class);
        Call<JsonResponse> call = weatherstackApi.getInfo(cityNameForQuery);

        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    errorMessageTextView.setText("code: " + response.code());
                    return;
                }
                frameLayout.setVisibility(View.GONE);

                JsonResponse cityWeathers = response.body();
                Location location = cityWeathers.getLocation();
                Current current = cityWeathers.getCurrent();

                countnryName.setText(location.getCountryName());
                cityName.setText(location.getCityName());
                temp.setText(current.getTemperature() + "\u2103");
                describtion.setText(current.getWeather_descriptions()[0]);
                percip.setText(current.getPrecip() + " mm");
                windSpeed.setText(current.getWind_speed() + " km/h");
                humidity.setText(current.getHumidity() + " %");
                recievedTemp.setText(String.valueOf(current.getFeelslike()));
                visibiltiy.setText(current.getVisibility() + " km");
                pressure.setText(current.getPressure() + " hPa");

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                errorMessageTextView.setVisibility(View.VISIBLE);
                errorMessageTextView.setText(t.getMessage());
            }
        });
    }

}
