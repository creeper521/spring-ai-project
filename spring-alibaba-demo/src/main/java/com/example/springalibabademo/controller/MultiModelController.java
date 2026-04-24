package com.example.springalibabademo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/multi")
public class MultiModelController {

    private final ChatClient chatClient;

    public MultiModelController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @RequestMapping("/image")
    public String image(String prompt) throws Exception{
        String url = "https://dashscope.oss-cn-beijing.aliyuncs.com/images/dog_and_girl.jpeg";
        List<Media> mediaList = List.of(new Media(MimeTypeUtils.IMAGE_JPEG, new URI(url).toURL().toURI()));
        //prompt
        UserMessage userMessage = UserMessage.builder().text(prompt).media(mediaList).build();
        //return AI result
        return this.chatClient.prompt(new Prompt(userMessage)).call().content();
    }
}
