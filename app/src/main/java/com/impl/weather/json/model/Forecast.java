package com.impl.weather.json.model;

import com.impl.weather.json.model.base.Main;
import com.impl.weather.json.model.base.Weather;

import java.util.List;

public class Forecast {
    private List<ArgList> list;

    public List<ArgList> getList() {
        return list;
    }

    public void setList(List<ArgList> list) {
        this.list = list;
    }

    class ArgList {
        private Main main;
        private List<Weather> weather;
        private String dt_txt;

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
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
}

