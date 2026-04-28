package com.example.springtoolcallingdemo.tool;

public class WeatherTools {
    /**
     * 伪实现查询天气
     * @param cityName
     * @return
     */
    String getCurrentWeatherByCityName(String cityName){
        switch (cityName){
            case "北京":
                return "北京天气好";
            case "上海":
                return "上海天气好好";
            case "深圳":
                return "深圳天气好好好";
            default:
                return "没有该城市的天气信息";
        }
    }
}
