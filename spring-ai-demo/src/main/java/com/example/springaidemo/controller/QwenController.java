package com.example.springaidemo.controller;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/qwen")
public class QwenController {
    @Autowired
    private OpenAiChatModel chatModel;

    @RequestMapping("/chat")
    public String chat(String message){
        return chatModel.call(message);
    }

    @RequestMapping("/chatWithPrompt")
    public String chatWithPrompt(String message){
        Prompt prompt = new Prompt(message);
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @RequestMapping("/chatWithRole")
    public String chatWithRole(String message){
        SystemMessage systemMessage = new SystemMessage("我是哈基米，是一只凶人的坏猫");
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @RequestMapping(value = "/stream", produces = "text/html;charset=utf-8")
    public Flux<String> stream(String message){
        Prompt prompt = new Prompt(message);
        Flux<ChatResponse> responseFlux = chatModel.stream(prompt);
        return responseFlux.map(x->x.getResult().getOutput().getText());
    }
}
