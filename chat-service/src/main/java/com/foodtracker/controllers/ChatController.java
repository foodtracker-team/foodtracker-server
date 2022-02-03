package com.foodtracker.controllers;

import com.foodtracker.models.*;
import com.foodtracker.models.message.ChatMessage;
import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.security.services.UserDetailsImpl;
import com.foodtracker.utilities.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import com.foodtracker.services.ChatMessageService;
import com.foodtracker.services.ChatRoomService;
import com.foodtracker.services.UserService;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/chat/")
public class ChatController {
  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  @Autowired
  private ChatMessageService chatMessageService;
  
  @Autowired
  private ChatRoomService chatRoomService;

  @Autowired
  private UserService userService;

  @Autowired
  private SessionManager sessionManager;




  @MessageMapping("/chat")
  public void processMessage(@Payload Map<String, Object> messageData, UsernamePasswordAuthenticationToken token) throws RestClientException {
    System.out.println(messageData);
    User user = sessionManager.getUserFromPrincipal((UserDetailsImpl) token.getPrincipal());
    final PublicUserModel actualUser = new PublicUserModel(user);
    final ChatMessage message = ChatMessage.create(messageData, actualUser);
    message.setAuthorId(actualUser.getId());

    final ChatMessage savedMessage = chatMessageService.save(message);
    savedMessage.setAuthorId(actualUser.getId());
    savedMessage.setAuthor(actualUser);
    savedMessage.setId((String) messageData.get("id"));

    ChatRoom room = chatRoomService.findChatRoomDetails(message.getRoomId());

    // Notify group users
    for( String groupUserId : room.getUsersId() ) {
      messagingTemplate.convertAndSendToUser(groupUserId, "/queue/messages", savedMessage);
    }

    // Notify moderators
    List<User> roomModerators = userService.getUsersByRole(room.getChannelRoles());
    for( User moderator : roomModerators ) {
      messagingTemplate.convertAndSendToUser(moderator.getId(), "/queue/messages", savedMessage);
    }
  }

  @GetMapping("/room/{roomId}/messages")
  public ResponseEntity<?> findChatMessages(@PathVariable String roomId) {
    return ResponseEntity.ok(chatMessageService.findChatMessages(roomId));
  }

  @GetMapping("/messages/{id}")
  public ResponseEntity<?> findMessage(@PathVariable String id) {
    return ResponseEntity.ok(chatMessageService.findById(id));
  }

  @GetMapping("/rooms")
  public ResponseEntity<?> findUserChatRooms() {
    List<ChatRoomResponse> userChatRooms = chatRoomService.findOrCreateUserRooms();
    return ResponseEntity.ok(userChatRooms);
  }

  @GetMapping("/room")
  public ResponseEntity<?> getChatRoomDetails(@RequestParam String roomId) {
    return ResponseEntity.ok(chatRoomService.findChatRoomDetails(roomId));
  }

  @PostMapping("/room")
  public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest roomData) {
    return ResponseEntity.ok(chatRoomService.createRoom(roomData));
  }

  @GetMapping("/isUpdated")
  public ResponseEntity<?> isUpdated() {
    return ResponseEntity.ok("11:43");
  }

}
