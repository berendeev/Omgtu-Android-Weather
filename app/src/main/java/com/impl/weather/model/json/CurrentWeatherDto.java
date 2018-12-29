package com.impl.weather.model.json;

import com.impl.weather.model.json.base.MainDto;
import com.impl.weather.model.json.base.WeatherDto;
import com.impl.weather.model.json.base.WindDto;

import java.util.List;

public class CurrentWeatherDto {
    private String name; //city name
    private List<WeatherDto> weather;
    private MainDto main;
    private WindDto wind;

    public WindDto getWind() {
        return wind;
    }

    public void setWind(WindDto wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDto> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDto> weather) {
        this.weather = weather;
    }


    public MainDto getMain() {
        return main;
    }

    public void setMain(MainDto main) {
        this.main = main;
    }
}
