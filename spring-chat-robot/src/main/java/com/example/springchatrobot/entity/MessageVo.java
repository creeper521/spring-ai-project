package com.example.springchatrobot.entity;

import lombok.Data;
import org.springframework.ai.chat.messages.Message;

@Data
public class MessageVo {

    private String role;
    private String content;

    public MessageVo(Message message) {
        switch (message.getMessageType()) {
            case USER -> this.role = "user";
            case ASSISTANT -> this.role = "assistant";
            case SYSTEM -> this.role = "system";
            case TOOL -> this.role = "tool";
            default -> this.role = "unknown";
        }
        this.content = message.getText();
    }
}
