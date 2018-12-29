package com.impl.weather.model.json;

import com.impl.weather.model.json.base.MainDto;
import com.impl.weather.model.json.base.WeatherDto;

import java.util.List;

public class ForecastDto {
    private List<ArgList> list;

    public List<ArgList> getList() {
        return list;
    }

    public void setList(List<ArgList> list) {
        this.list = list;
    }

    class ArgList {
        private MainDto main;
        private List<WeatherDto> weather;
        private String dt_txt;

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
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
}

