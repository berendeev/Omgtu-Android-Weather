package com.impl.weather.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.impl.weather.R;
import com.impl.weather.WeatherUtils.WeatherUtils;
import com.impl.weather.model.db.Main;
import com.impl.weather.model.db.Weather;
import com.impl.weather.model.db.Weathers;
import com.impl.weather.model.db.Wind;
import com.impl.weather.model.json.CurrentWeatherDto;

import java.io.IOException;
import java.net.URL;

public class ShowWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        Intent intent = getIntent();
        String response = intent.getStringExtra(MainActivity.CURRENT_TEMP);

        CurrentWeatherDto currentWeather = new Gson().fromJson(response, CurrentWeatherDto.class);
        Weathers current = WeatherUtils.dtoToModelMap(currentWeather);
        current.save();

        showWeather(current);
    }



    private void showWeather(final Weathers currentWeather) {
        TextView cityName = findViewById(R.id.tv_city_name);
        TextView weather = findViewById(R.id.tv_weather);
        TextView humidity = findViewById(R.id.tv_humidity);
        TextView windSpeed = findViewById(R.id.tv_wind_speed);
        final ImageView imageView = findViewById(R.id.iv_icon);


        cityName.setText(currentWeather.getName());
        weather.setText(String.valueOf(currentWeather.getMain().getTemp()));
        humidity.setText(String.valueOf(currentWeather.getMain().getHumidity()));
        windSpeed.setText(String.valueOf(currentWeather.getWind().getSpeed()));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadImage(imageView, currentWeather.getWeather().getIcon());
            }
        });

    }

    private void loadImage(final ImageView imageView, final String idImage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                Bitmap bitmap;
                try {
                    url = new URL("http://openweathermap.org/img/w/" + idImage + ".png");
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    final Bitmap finalBitmap = bitmap;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(finalBitmap);
                        }
                    });
                } catch (IOException exc) {
                    //do nothing
                }
            }
        }).start();
    }
}
