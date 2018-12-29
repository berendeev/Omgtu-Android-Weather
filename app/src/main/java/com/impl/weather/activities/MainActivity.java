package com.impl.weather.activities;


import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.impl.weather.R;
import com.impl.weather.WeatherUtils.WeatherUtils;
import com.impl.weather.model.db.Main;
import com.impl.weather.model.db.Weather;
import com.impl.weather.model.db.Weathers;
import com.impl.weather.model.db.Wind;
import com.impl.weather.services.WeatherService;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    public static final String CURRENT_TEMP = "CURRENT_TEMPERATURE_RESPONSE_JSON";
    public static final String UNIT = "UNIT_FOR_REQUEST";
    public static final String CITY = "CITY_FOR_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.btn_request);
        final RadioGroup radioGroup = findViewById(R.id.rg_unit);
        final EditText cityName = findViewById(R.id.t_cityName);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit = getUnit(radioGroup);
                request(String.valueOf(cityName.getText()), unit);
            }
        });

        Button btnList = findViewById(R.id.btn_list);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WeatherListActivity.class);
                startActivity(intent);
            }
        });
        btnList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                Weathers.deleteAll(Weathers.class);
                Main.deleteAll(Main.class);
                Weather.deleteAll(Weather.class);
                Wind.deleteAll(Wind.class);
                return true;
            }
        });

        Switch service = findViewById(R.id.sw_service);
        service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!cityName.getText().toString().equals("")) {

                        Intent intent = new Intent(MainActivity.this, WeatherService.class);
                        intent.putExtra(UNIT, getUnit(radioGroup));
                        intent.putExtra(CITY, String.valueOf(cityName.getText()));
                        startService(intent);

                    } else {
                        buttonView.toggle();
                    }
                } else {
                    stopService(new Intent(MainActivity.this, WeatherService.class));
                }
            }
        });
    }

    private String getUnit(RadioGroup radioGroup) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_celsius:
                return "units=metric";
            case R.id.rb_fahrenheit:
                return "units=imperial";
            case R.id.rb_kelvin:
                return "";
            default:
                return "units=metric";
        }
    }

    private void request(String cityName, String unit) {
        final ProgressDialog nDialog;
        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Get Data");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);

        URL url = WeatherUtils.makeURL(cityName, unit);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        final Intent intent = new Intent(this, ShowWeatherActivity.class);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    final String content = responseBody.string();
                    responseBody.close();

                    intent.putExtra(CURRENT_TEMP, content);
                    nDialog.dismiss();
                    startActivity(intent);
                }
            }
        });
    }


}
