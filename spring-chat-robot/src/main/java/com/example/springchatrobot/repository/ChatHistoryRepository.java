package com.example.springchatrobot.repository;

import com.example.springchatrobot.entity.ChatInfo;

import java.util.List;

public interface ChatHistoryRepository {

    void save(String chatId, String title);

    void clearByChatId(String chatId);

    List<ChatInfo> getChats();
}
