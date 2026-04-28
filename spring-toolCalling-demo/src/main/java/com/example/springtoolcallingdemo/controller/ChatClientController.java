package com.example.springtoolcallingdemo.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.example.springtoolcallingdemo.tool.DateTimeTools;
import com.example.springtoolcallingdemo.tool.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

@RequestMapping("chatByClient")
@RestController
public class ChatClientController {
    private ChatClient chatClient;

    public ChatClientController(DashScopeChatModel chatModel){
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultTools(new DateTimeTools())
                .build();
    }

    @Autowired
    public ChatClientController(DashScopeChatModel chatModel, ToolCallback weatherTool){
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultTools(new DateTimeTools())
                .defaultToolCallbacks(weatherTool)
                .build();
    }

    @RequestMapping("/call")
    public String call(String message){
        return chatClient
                .prompt()
                .user(message)
//                .tools(new DateTimeTools())
                .toolContext(Map.of("chatId", "chatId-01"))
                .call()
                .content();
    }

    @RequestMapping("/call1")
    public String call1(String message){
        Method method = ReflectionUtils.findMethod(WeatherTools.class,
                "getCurrentWeatherByCityName", String.class);
        ToolCallback toolCallback = MethodToolCallback
                .builder()
                .toolDefinition(ToolDefinitions
                                .builder(method)
                                .description("根据给定的城市名称，获取当前天气")
                                .build())
                .toolMethod(method)
                .toolObject(new WeatherTools())
                .build();

        return chatClient
                .prompt()
                .user(message)
                .toolContext(Map.of("chatId", "chatId-01"))
                .toolCallbacks(toolCallback)
                .call()
                .content();
    }

}
