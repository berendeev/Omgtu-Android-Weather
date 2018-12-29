package com.impl.weather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.impl.weather.R;
import com.impl.weather.model.db.Weathers;

import java.util.List;

public class WeatherListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);

        fill();
    }

    private void fill() {
        List<Weathers> list = Weathers.listAll(Weathers.class);

        LinearLayout ll = findViewById(R.id.ll_weathers_list);
        for (Weathers weathers : list) {
            LinearLayout llRecord = new LinearLayout(getApplicationContext());
            llRecord.setOrientation(LinearLayout.HORIZONTAL);
            llRecord.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tvDate = new TextView(getApplicationContext());
            tvDate.setText(weathers.getDate().toString());
            llRecord.addView(tvDate);

            TextView tvCity = new TextView(getApplicationContext());
            tvCity.setText(weathers.getName());
            llRecord.addView(tvCity);

            TextView tvTemp = new TextView(getApplicationContext());
            tvTemp.setText(String.valueOf(weathers.getMain().getTemp()));
            llRecord.addView(tvTemp);

            ll.addView(llRecord);
        }
    }
}
