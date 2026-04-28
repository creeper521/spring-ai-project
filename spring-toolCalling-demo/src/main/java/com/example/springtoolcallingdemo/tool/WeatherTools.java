package com.example.springtoolcallingdemo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class WeatherTools {
    /**
     * 伪实现查询天气
     * @param cityName
     * @return
     */
    @Tool(description = "根据城市名称查询当前天气")
    public String getCurrentWeatherByCityName(@ToolParam(description = "城市名称，比如北京、上海、深圳")String cityName){
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
