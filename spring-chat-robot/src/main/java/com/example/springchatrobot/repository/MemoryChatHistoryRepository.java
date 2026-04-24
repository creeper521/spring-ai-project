package com.example.springchatrobot.repository;

import com.example.springchatrobot.entity.ChatInfo;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryChatHistoryRepository implements ChatHistoryRepository {

    private final Map<String, String> chatInfos = new LinkedHashMap<>();

    @Override
    public synchronized void save(String chatId, String title) {
        chatInfos.put(chatId, title);
    }

    @Override
    public synchronized void clearByChatId(String chatId) {
        chatInfos.remove(chatId);
    }

    @Override
    public synchronized List<ChatInfo> getChats() {
        return chatInfos.entrySet()
                .stream()
                .map(entry -> new ChatInfo(entry.getKey(), entry.getValue()))
                .toList();
    }
}
