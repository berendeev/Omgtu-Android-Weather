package com.impl.weather.json.model;

import com.impl.weather.json.model.base.Main;
import com.impl.weather.json.model.base.Weather;
import com.impl.weather.json.model.base.Wind;

import java.util.List;

public class CurrentWeather {
    private String name; //city name
    private List<Weather> weather;
    private Main main;
    private Wind wind;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
