package com.foodtracker.models.room;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.foodtracker.models.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
  @Id
  private String id;
  private String name;
  private Date createdAt;
  private Date updatedAt;
  private String imageId;
  private List<ERole> channelRoles;
  private Map<String, Object> metadata;
  private RoomType type;
  private List<String> usersId;

  public ChatRoom(RoomType type, List<String> usersId) {
    this.type = type;
    this.usersId = usersId;
  }

  public ChatRoom(String id, String name, Date createdAt, Date updatedAt, String imageId, List<ERole> channelRoles, Map<String, Object> metadata, RoomType type, List<String> usersId) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.imageId = imageId;
    this.channelRoles = channelRoles;
    this.metadata = metadata;
    this.type = type;
    this.usersId = usersId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ChatRoom)) return false;
    ChatRoom chatRoom = (ChatRoom) o;
    return id.equals(chatRoom.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
