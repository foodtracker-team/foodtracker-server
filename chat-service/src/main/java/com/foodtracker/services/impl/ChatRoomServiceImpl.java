package com.foodtracker.services.impl;

import com.foodtracker.models.*;
import com.foodtracker.models.message.ChatMessage;
import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.models.room.RoomType;
import com.foodtracker.repositories.mongo.ChatRoomRepository;
import com.foodtracker.services.ChatMessageService;
import com.foodtracker.services.ChatRoomService;
import com.foodtracker.services.UserService;
import com.foodtracker.utilities.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private ChatMessageService chatMessageService;

  @Autowired
  private UserService userService;

  @Autowired
  private SessionManager sessionManager;

  @Override
  public List<ChatRoomResponse> findOrCreateUserRooms() {
    User user = sessionManager.getUser();

    List<ChatRoom> defaultRooms = _getDefaultUserRooms(user);
    List<ChatRoom> roleRooms = _getRoleChatRooms();
    List<ChatRoom> privateRooms = _getAllUserRooms(user);
    List<ChatRoomResponse> chatRoomResponse = new ArrayList<>();
    Set<ChatRoom> allRooms = new HashSet<>();
    allRooms.addAll(defaultRooms);
    allRooms.addAll(roleRooms);
    allRooms.addAll(privateRooms);
    for(ChatRoom room : allRooms ) {
      chatRoomResponse.add(getChatRoomResponse(room));
    }
    return chatRoomResponse;
  }

  private List<ChatRoom> _getAllUserRooms(User user) {
    Optional<List<ChatRoom>> userRoomsOptional = chatRoomRepository.findUserRooms(user.getId());
    return userRoomsOptional.orElseGet(ArrayList::new);
  }

  private List<ChatRoom> _getRoleChatRooms() {
    List<ERole> readChatRoles = Arrays.asList(ERole.READ_CHAT_BUSINESS_ROOM, ERole.READ_CHAT_TECHNIC_ROOM);
    List<ChatRoom> roleRooms = new ArrayList<>();
    for (ERole role : readChatRoles) {
      if (sessionManager.containsRole(role)) {
        Optional<List<ChatRoom>> userRoomsOptional = chatRoomRepository.findByChannelRoles(role);
        userRoomsOptional.ifPresent(roleRooms::addAll);
      }
    }
    return roleRooms;
  }

  private List<ChatRoom> _getDefaultUserRooms(User user) {
    Optional<List<ChatRoom>> channelsRoomsOptional = chatRoomRepository.findByTypeAndUserId(RoomType.channel, user.getId());
    System.out.println(channelsRoomsOptional);
    List<ChatRoom> channelsRooms = new ArrayList<>();
    channelsRoomsOptional.ifPresent(channelsRooms::addAll);
    DefaultChatRoomsFactory defaultChatRoomsFactory = new DefaultChatRoomsFactory();

    List<ChatRoom> defaultChatRooms = defaultChatRoomsFactory.getNotExistingChatRooms(user, channelsRooms);
    defaultChatRooms = chatRoomRepository.saveAll(defaultChatRooms);

    channelsRooms.addAll(defaultChatRooms);
    return channelsRooms;
  }

  @Override
  public ChatRoom findChatRoomDetails(String roomId) {
    Optional<ChatRoom> room = chatRoomRepository.findById(roomId);
    return room.orElse(null);
  }

  public ChatRoomResponse getChatRoomResponse(ChatRoom room) {
    final ChatMessage lastMessage = chatMessageService.findLastChatMessageForRoom(room.getId());
    List<PublicUserModel> roomUsers = new ArrayList<>();
    for (String userId : room.getUsersId()) {
      roomUsers.add(new PublicUserModel(userService.getUserById(userId)));
    }
    return new ChatRoomResponse(room, lastMessage, roomUsers);
  }

  @Override
  public ChatRoom createRoom(CreateRoomRequest roomData) {
    RoomType roomType = RoomType.direct;
    if (roomData.getUsers().size() > 2) {
      roomType = RoomType.group;
    }
    final ChatRoom chatRoom = new ChatRoom(roomType, roomData.getUsers());

    chatRoomRepository.save(chatRoom);
    return chatRoom;
  }

}
