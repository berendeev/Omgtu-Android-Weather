package com.impl.weather.model.db;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.Date;

public class Weathers extends SugarRecord<Weathers> {
    private String name; //city name
    private Weather weather;
    private Main main;
    private Wind wind;
    private Date date;

    public Weathers() {
    }

    public Weathers(String name, Weather weather, Main main, Wind wind) {
        this.name = name;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        date = Calendar.getInstance().getTime();
    }

    @Override
    public void save() {
        weather.save();
        main.save();
        wind.save();
        super.save();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
