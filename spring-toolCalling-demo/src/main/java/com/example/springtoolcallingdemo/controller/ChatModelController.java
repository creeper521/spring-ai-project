package com.example.springtoolcallingdemo.controller;

import com.example.springtoolcallingdemo.tool.DateTimeTools;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/callByTool")
    public String callByTool(String message){
        ToolCallback[] dataTimeTools = ToolCallbacks.from(new DateTimeTools());
        ChatOptions chatOptions = ToolCallingChatOptions
                .builder()
                .toolCallbacks(dataTimeTools)
                .build();
        Prompt prompt = new Prompt(message, chatOptions);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

}
