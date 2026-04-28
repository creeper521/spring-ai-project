package com.example.springtoolcallingdemo.tool;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

public class DateTimeTools {

    /**
     * 查询当前时间
     * @param toolContext
     * @return
     */
    @Tool(description = " Get the current date and time in the user's timezone")
    String getCurrentDateTime(ToolContext toolContext){
        System.out.println("chatId " + toolContext.getContext().get("chatId"));
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();//获取当前时区的时间
    }

    /**
     * 设置闹钟
     * @param time
     */
    @Tool(description = "Set a user alarm for the given time")
    void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time){
        System.out.println("Alarm set for " + time);
    }
}
