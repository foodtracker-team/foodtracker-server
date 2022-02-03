package com.foodtracker.models;

import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.models.room.RoomType;

import java.util.*;

public class DefaultChatRoomsFactory {

  public DefaultChatRoomsFactory() {
  }

  public List<ChatRoom> getNotExistingChatRooms(User user, List<ChatRoom> chatRooms) {
    List<ChatRoom> finalChatRooms = new ArrayList<>();

    for( Role role : user.getRoles() ) {
      switch( role.getName() ) {
        case HAS_CHAT_BUSINESS_ROOM:
          if(!isRoomsContainsRole(chatRooms, ERole.READ_CHAT_BUSINESS_ROOM)) {
            finalChatRooms.add(buildBusinessContactRoom(user));
          }
          break;
        case HAS_CHAT_TECHNIC_ROOM:
          if(!isRoomsContainsRole(chatRooms, ERole.READ_CHAT_TECHNIC_ROOM)) {
            finalChatRooms.add(buildTechnicContactRoom(user));
          }
      }
    }
    System.out.println(finalChatRooms);
    return finalChatRooms;
  }

  private boolean isRoomsContainsRole(List<ChatRoom> chatRooms, ERole role) {
    for( ChatRoom chatRoom : chatRooms ) {
      for( ERole roomRole : chatRoom.getChannelRoles()) {
        if( roomRole == role ) {
          return true;
        }
      }
    }
    return false;
  }

  private ChatRoom buildBusinessContactRoom(User user) {
    ChatRoom chatRoom = new ChatRoom();
    Map<String, Object> metadata = new HashMap<>();
    metadata.put("type", "CHAT_BUSINESS_ROOM");
    List<ERole> channelRoles = new ArrayList<>();
    channelRoles.add(ERole.READ_CHAT_BUSINESS_ROOM);
    chatRoom.setType(RoomType.channel);
    chatRoom.setChannelRoles(channelRoles);
    chatRoom.setCreatedAt(new Date());
    chatRoom.setUpdatedAt(new Date());
    chatRoom.setImageId("CHAT_BUSINESS_ROOM");
    chatRoom.setName("Kontakt biznesowy");
    chatRoom.setMetadata(metadata);
    List<String> usersIds = new ArrayList<>();
    usersIds.add(user.getId());
    chatRoom.setUsersId(usersIds);
    return chatRoom;
  }

  private ChatRoom buildTechnicContactRoom(User user) {
    ChatRoom chatRoom = new ChatRoom();
    Map<String, Object> metadata = new HashMap<>();
    metadata.put("type", "CHAT_TECHNIC_ROOM");
    List<ERole> channelRoles = new ArrayList<>();
    channelRoles.add(ERole.READ_CHAT_TECHNIC_ROOM);
    chatRoom.setType(RoomType.channel);
    chatRoom.setChannelRoles(channelRoles);
    chatRoom.setCreatedAt(new Date());
    chatRoom.setUpdatedAt(new Date());
    chatRoom.setImageId("CHAT_TECHNIC_ROOM");
    chatRoom.setName("Kontakt techniczny");
    chatRoom.setMetadata(metadata);
    List<String> usersIds = new ArrayList<>();
    usersIds.add(user.getId());
    chatRoom.setUsersId(usersIds);
    return chatRoom;
  }
}
