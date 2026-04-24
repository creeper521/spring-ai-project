package com.example.springchatrobot.controller;

import com.example.springchatrobot.entity.ChatInfo;
import com.example.springchatrobot.entity.MessageVo;
import com.example.springchatrobot.repository.ChatHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final ChatHistoryRepository chatHistoryRepository;

    public ChatController(
            ChatClient qwenChatClient,
            ChatMemory chatMemory,
            ChatHistoryRepository chatHistoryRepository
    ) {
        this.chatClient = qwenChatClient;
        this.chatMemory = chatMemory;
        this.chatHistoryRepository = chatHistoryRepository;
    }

    @RequestMapping(value = "/stream", produces = "text/html;charset=utf-8")
    public Flux<String> stream(String prompt, String chatId) {
        log.info("chatId:{}, prompt:{}", chatId, prompt);
        chatHistoryRepository.save(chatId, prompt);
        return this.chatClient
                .prompt()
                .user(prompt)
                .advisors(spec -> spec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    @RequestMapping("/getChatIds")
    public List<ChatInfo> getChatIds() {
        return chatHistoryRepository.getChats();
    }

    @RequestMapping("/getChatHistory")
    public List<MessageVo> getChatHistory(String chatId) {
        log.info("get chat history, chatId:{}", chatId);
        List<Message> messages = chatMemory.get(chatId,15);
        return messages.stream().map(MessageVo::new).toList();
    }

    @RequestMapping("/deleteChat")
    public Boolean deleteChat(String chatId) {
        log.info("delete chat, chatId:{}", chatId);
        try {
            chatHistoryRepository.clearByChatId(chatId);
            chatMemory.clear(chatId);
            return true;
        } catch (Exception e) {
            log.error("delete chat failed, chatId:{}", chatId, e);
            return false;
        }
    }
}
