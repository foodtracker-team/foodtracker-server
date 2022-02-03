package com.foodtracker.services;

import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.models.ChatRoomResponse;
import com.foodtracker.models.CreateRoomRequest;

import java.util.List;

public interface ChatRoomService {
    /**
     * Finds all user rooms basing on session user
     * Creates user default rooms if not exists
     * @return - Returns list of user chat rooms
     */
    List<ChatRoomResponse> findOrCreateUserRooms();

    /**
     * Finds chat room by provided id
     * @return - Returns full chat room details
     */
    ChatRoom findChatRoomDetails(String roomId);

    /**
     * Saving ChatRoom in database
     * @return - ChatRoom with assigned data
     */
    ChatRoom createRoom(CreateRoomRequest roomData);

}
