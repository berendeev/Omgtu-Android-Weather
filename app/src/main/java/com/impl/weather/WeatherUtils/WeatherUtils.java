package com.impl.weather.WeatherUtils;

import com.impl.weather.model.db.Main;
import com.impl.weather.model.db.Weather;
import com.impl.weather.model.db.Weathers;
import com.impl.weather.model.db.Wind;
import com.impl.weather.model.json.CurrentWeatherDto;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherUtils {
    public static URL makeURL(String cityName, String unit) {
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
        }
        return url;
    }


    public static Weathers dtoToModelMap(CurrentWeatherDto dto) {
        Weather weather = new Weather(dto.getWeather().get(0).getMain(), dto.getWeather().get(0).getDescription(),
                dto.getWeather().get(0).getIcon());

        Main main = new Main(dto.getMain().getTemp(), dto.getMain().getTemp_max(), dto.getMain().getTemp_min(),
                dto.getMain().getHumidity());

        Wind wind = new Wind(dto.getWind().getSpeed());

        return new Weathers(dto.getName(), weather, main, wind);
    }
}
