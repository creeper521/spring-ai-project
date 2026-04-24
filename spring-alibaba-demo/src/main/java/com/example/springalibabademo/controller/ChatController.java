package com.example.springalibabademo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/call")
    public String generation(String userInput){
        return this.chatClient
                .prompt()
                .user(userInput)//用户输入
                .call()//调用AI
                .content();//返回相应
    }

    record Recipe(String dish, List<String> ingredients){}

    @RequestMapping("/entity")
    public String entity(String userInput){
        Recipe recipe = chatClient.prompt()
                .user(String.format("请帮我生成%s的食谱", userInput))
                .call()
                .entity(Recipe.class);
        return recipe.toString();
    }

    @GetMapping(value = "/stream", produces = "text/html;charset=utf-8")
    public Flux<String> stream(String userInput){
        return this.chatClient
                .prompt()
                .user(userInput)
                .stream()
                .content();
    }

    /**
     * 围绕关键词回复
     * @param message
     * @param word
     * @return
     */
    @GetMapping("/word")
    public String word(String message, String word){
        return chatClient
                .prompt()
                .system(s -> s.param("word", word))
                .user(message)
                .call()
                .content();
    }
}
