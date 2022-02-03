package com.foodtracker.models;

import com.foodtracker.models.message.ChatMessage;
import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.models.room.RoomType;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChatRoomResponse {
  private String id;
  private String name;
  private Date createdAt;
  private Date updatedAt;
  private String imageId;
  private Map<String, Object> metadata;
  private RoomType type;
  private List<PublicUserModel> users;
  private ChatMessage lastChatMessage;

  public ChatRoomResponse() {
  }

  public ChatRoomResponse(ChatRoom room, ChatMessage lastChatMessage, List<PublicUserModel> users) {
    this.id = room.getId();
    this.name = room.getName();
    this.createdAt = room.getCreatedAt();
    this.updatedAt = room.getUpdatedAt();
    this.imageId = room.getImageId();
    this.lastChatMessage = lastChatMessage;
    this.metadata = room.getMetadata();
    this.type = room.getType();
    this.users = users;
  }

  public ChatRoomResponse(String id, String name, Date createdAt, Date updatedAt, String imageId, ChatMessage lastChatMessage, Map<String, Object> metadata, RoomType type, List<PublicUserModel> users) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.imageId = imageId;
    this.lastChatMessage = lastChatMessage;
    this.metadata = metadata;
    this.type = type;
    this.users = users;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public ChatMessage getLastChatMessage() {
    return lastChatMessage;
  }

  public void setLastChatMessage(ChatMessage lastChatMessage) {
    this.lastChatMessage = lastChatMessage;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public RoomType getType() {
    return type;
  }

  public void setType(RoomType type) {
    this.type = type;
  }

  public List<PublicUserModel> getUsers() {
    return users;
  }

  public void setUsers(List<PublicUserModel> users) {
    this.users = users;
  }
}
