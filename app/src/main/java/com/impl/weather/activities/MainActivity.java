package com.impl.weather.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.impl.weather.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    public static final String CURRENT_TEMP = "CURRENT_TEMPERATURE_RESPONSE_JSON";

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

        URL url = makeURL(cityName, unit);

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
        nDialog.show();
    }

    private URL makeURL(String cityName, String unit) {
        String appKey = "APPID=3352db8eb094658bf5704ba8c5f73078";

        StringBuilder strUrl = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?q=");
        strUrl.append(cityName);
        if (!unit.isEmpty()) {
            strUrl.append("&").append(unit);
        }
        strUrl.append("&").append(appKey);

        URL url = null;
        try {
            url = new URL(strUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Bad url", Toast.LENGTH_SHORT).show();
        }
        return url;
    }
}
