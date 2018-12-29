package com.impl.weather.model.db;

import com.orm.SugarRecord;

public class Main extends SugarRecord<Main>{
    private double temp;
    private double temp_max;
    private double temp_min;
    private double humidity;

    public Main() {
    }

    public Main(double temp, double temp_max, double temp_min, double humidity) {
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.humidity = humidity;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }
}
