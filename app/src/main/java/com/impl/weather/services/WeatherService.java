package com.impl.weather.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.impl.weather.R;
import com.impl.weather.WeatherUtils.WeatherUtils;
import com.impl.weather.activities.MainActivity;
import com.impl.weather.model.db.Weathers;
import com.impl.weather.model.json.CurrentWeatherDto;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherService extends Service {
    AtomicBoolean isStopping;
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        isStopping = new AtomicBoolean(false);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        isStopping.set(true);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String city = intent.getStringExtra(MainActivity.CITY);
        String unit = intent.getStringExtra(MainActivity.UNIT);
        URL url = WeatherUtils.makeURL(city, unit);
        requestLoop(url);
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLoop(final URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    while (!isStopping.get()) {
                        Response response = client.newCall(request).execute();
                        CurrentWeatherDto dto = gson.fromJson(response.body().string(), CurrentWeatherDto.class);

                        Weathers weathers = WeatherUtils.dtoToModelMap(dto);
                        weathers.save();

                        notifications(weathers);
                        Thread.sleep(10000);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notifications(Weathers weathers) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Weather " + weathers.getName())
                .setContentText("In the city " + weathers.getName() + " " + weathers.getMain().getTemp())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(1, mBuilder.build());
    }
}
