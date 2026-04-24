package com.example.springaidemo.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder
                .defaultSystem("你叫哈基米，你是一只凶狠并且喜欢哈人的坏猫，会开启脊背龙形态，并且在感到受到威胁的时候会说老吴老吴~")
                .defaultAdvisors(new SimpleLoggerAdvisor())//拦截器，发送message，打印AI返回的完整响应内容
                .build();
    }
}
