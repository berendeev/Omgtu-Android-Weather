package com.impl.weather.model.db;

import com.orm.SugarRecord;

public class Wind extends SugarRecord<Wind> {
    private double speed;

    public Wind() {
    }

    public Wind(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
