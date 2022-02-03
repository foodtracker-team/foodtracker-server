package com.foodtracker.services.impl;


import java.util.*;

import com.foodtracker.models.PublicUserModel;
import com.foodtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodtracker.models.message.ChatMessage;
import com.foodtracker.models.message.MessageStatus;
import com.foodtracker.repositories.mongo.ChatMessageRepository;
import com.foodtracker.services.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
  @Autowired
  private ChatMessageRepository repository;

  @Autowired
  private UserService userService;

  @Override
  public ChatMessage save(ChatMessage chatMessage) {
    chatMessage.setId(UUID.randomUUID().toString());
    chatMessage.setStatus(MessageStatus.sent);
    chatMessage.setCreatedAt(new Date());
    chatMessage.setUpdatedAt(new Date());
    System.out.println(chatMessage);
    repository.save(chatMessage);
    return chatMessage;
  }

  @Override
  public ChatMessage findLastChatMessageForRoom(String roomId) {
    return repository.findTopByRoomIdOrderByCreatedAtDesc(roomId);
  }

  @Override
  public List<ChatMessage> findChatMessages(String roomId) {
    final List<ChatMessage> messages = repository.findByRoomIdOrderByCreatedAtDesc(roomId);
    fillMessages(messages);
    return messages;
  }

  @Override
  public Optional<ChatMessage> findById(String id) {
    return repository.findById(id).map(chatMessage -> {
      chatMessage.setStatus(MessageStatus.delivered);
      ChatMessage message = repository.save(chatMessage);
      fillMessage(message);
      return message;
    });
  }

  @Override
  public void fillMessages(List<ChatMessage> chatMessages) {
    for( ChatMessage chatMessage : chatMessages ) {
      fillMessage(chatMessage);
    }
  }

  @Override
  public void fillMessage(ChatMessage message) {
    PublicUserModel publicUser = new PublicUserModel(userService.getUserById(message.getAuthorId()));
    message.setAuthor(publicUser);
  }
}
