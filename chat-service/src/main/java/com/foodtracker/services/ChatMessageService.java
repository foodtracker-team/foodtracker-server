package com.foodtracker.services;

import com.foodtracker.models.message.ChatMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChatMessageService {
    /**
     * Saves chat message in MongoDB database
     * @param chatMessage - ChatMessage Object
     * @return - ChatMessage object with assigned parameters
     */
    ChatMessage save(ChatMessage chatMessage);

    /**
     * Finds last chat message sent in selected room
     * @param roomId - Room ID parameter
     * @return - Returns last chat message
     */
    ChatMessage findLastChatMessageForRoom(String roomId);


    /**
     * Finds all messages from selected room
     * @param roomId - Room ID parameter
     * @return - Returns list of ChatMessage from room
     */
    // TODO(dominik): Allow to range search
    //                (example: page1, page2 or from index 0 to index 20)
    List<ChatMessage> findChatMessages(String roomId);

    /**
     * Finds message by provided ID
     * @param id - Searched message id
     * @return - ChatMessage if its found
     */
    Optional<ChatMessage> findById(String id);

    /**
     * Fills List of chat messages with required data.
     * For example replaces authorId to PublicUserModel author
     *
     * It has to be called before every return to frontend
     * @param chatMessages - List of messages to be filled
     */
    void fillMessages(List<ChatMessage> chatMessages);

    /**
     * Fills chat message with required data.
     * For example replaces authorId to PublicUserModel author
     * @param message - Object of message to be filled
     */
    void fillMessage(ChatMessage message);
}
