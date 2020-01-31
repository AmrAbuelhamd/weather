package com.blogspot.soyamr.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        //populate the autoCompleteTextView with the array of cities
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.cities_array));
        autoCompleteTextView.setAdapter(adapter);

        //set listener when user choose element from the list, to hid the list itself
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        });
    }

    // when user clicks the button this gets executed to launch the weather activity
    public void getCityWeather(View view) {
        String cityName = autoCompleteTextView.getText().toString();

        //handles case when user press the button without typing anything or just typing blank spaces
        if (!cityName.trim().isEmpty()) {
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("city name", cityName);
            startActivity(intent);
        } else {
            autoCompleteTextView.setError("please write something");
        }
    }

}