package com.blogspot.soyamr.weather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.blogspot.soyamr.weather.model.Current;
import com.blogspot.soyamr.weather.model.Error;
import com.blogspot.soyamr.weather.model.JsonResponse;
import com.blogspot.soyamr.weather.model.Location;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
this class uses retrofit to get the weather info from api and uses picaso to donwload a small photo

 */
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
    private ProgressBar progressBar;
    private TextView errorMessageTextView;
    private ConstraintLayout constraintLayout;
    String cityNameForQuery;
    private TextView localTime;
    private Group group;

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
        progressBar = findViewById(R.id.progress_bar);
        errorMessageTextView = findViewById(R.id.error_message);
        localTime = findViewById(R.id.local_time);
        constraintLayout = findViewById(R.id.constraintLayout);
        group = findViewById(R.id.groupForAllOthers);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        //get's the city name from the previous activity
        Intent intent = getIntent();
        cityNameForQuery = intent.getStringExtra("city name");

        findViews();

        startRetrofitWork();
    }

    private void startRetrofitWork() {

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
                    showErrorMesg("code: " + response.code());
                    return;
                }

                JsonResponse cityWeathers = response.body();

                //gets the error class with detailed info about the error
                Error error = cityWeathers.getError();
                if (error != null) {
                    StringBuilder errorMsg = new StringBuilder("");
                    errorMsg.append("code: " + error.getCode());
                    errorMsg.append("\ntype: " + error.getType());
                    errorMsg.append("\ninfo: " + error.getInfo());

                    showErrorMesg(errorMsg.toString());
                    return;
                }

                //in case of no errors at all then this means that i got a real data clean of any errors
                Location location = cityWeathers.getLocation();
                Current current = cityWeathers.getCurrent();
                initializeViews(location, current);

            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                showErrorMesg(t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /*
    initialize The Views To The Data Received From the Api
     */
    private void initializeViews(Location location, Current current) {
        //hide the error components
        progressBar.setVisibility(View.GONE);


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

        //loads the photo that expresses the weather state
        Picasso.get().load(current.getWeather_Icons()[0]).into(imageView);

        //initialize the background of the layout according to the time in that city weather it's
        //day or night
        if (current.getIs_day().equals("yes")) {
            constraintLayout.setBackgroundResource(R.drawable.day);
        } else {
            constraintLayout.setBackgroundResource(R.drawable.night);
        }

        //i am getting the full date from the api, here i am cutting the time part of it
        String time = location.getLocaltime();
        time = time.substring(10);
        localTime.setText(time);
    }

    /*
    hides the lading indicator and shoes the textView and shows the user the error
     */
    void showErrorMesg(String msg) {

        group.setVisibility(View.GONE);//hides all other components
        progressBar.setVisibility(View.GONE);

        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(msg);
    }
}
