package com.example.springtoolcallingdemo.controller;

import com.example.springtoolcallingdemo.tool.DateTimeTools;
import com.example.springtoolcallingdemo.tool.WeatherTools;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.support.ToolDefinitions;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RequestMapping("/chatByModel")
@RestController
public class ChatModelController {
    private ChatModel chatModel;

    public ChatModelController(ChatModel chatModel){
        this.chatModel = chatModel;
    }

    @RequestMapping("/call")
    public String call(String message){
        return chatModel.call(message);
    }

    /**
     * 查询当前时间
     * @param message
     * @param chatId
     * @return
     */
    @RequestMapping("/callByDateTool")
    public String callByDateTool(String message, String chatId){
        ToolCallback[] dataTimeTools = ToolCallbacks.from(new DateTimeTools());
        ChatOptions chatOptions = ToolCallingChatOptions
                .builder()
                .toolCallbacks(dataTimeTools)
                .toolContext("chatId", chatId)
                .build();
        Prompt prompt = new Prompt(message, chatOptions);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @RequestMapping("/callByWeatherTool")
    public String callByWeatherTool(String message, String chatId){
        Method method = ReflectionUtils.findMethod(WeatherTools.class,
                "getCurrentWeatherByCityName", String.class);
        ToolCallback toolCallback = MethodToolCallback
                .builder()
                .toolDefinition(
                        ToolDefinitions
                                .builder(method)
                                .description("根据当前的城市名称，获取当前城市天气")
                                .build())
                .toolMethod(method)
                .toolObject(new WeatherTools())
                .build();

        ChatOptions chatOptions = ToolCallingChatOptions
                .builder()
                .toolCallbacks(toolCallback)
                .toolContext("chatId", chatId)
                .build();

        Prompt prompt = new Prompt(message, chatOptions);

        return chatModel.call(prompt).getResult().getOutput().getText();
    }

}
