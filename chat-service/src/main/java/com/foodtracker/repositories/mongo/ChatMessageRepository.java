package com.foodtracker.repositories.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import com.foodtracker.models.message.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

  List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(String chatId);

  ChatMessage findTopByRoomIdOrderByCreatedAtDesc(String chatId);
}
