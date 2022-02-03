package com.foodtracker.models.message;


import com.foodtracker.models.PublicUserModel;
import com.foodtracker.models.message.types.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;

/**
 * General class that defines message
 * from chat which is sent from user
 *
 * Classes that inherit from Chat Message:
 * - CustomMessage
 * - FileMessage
 * - ImageMessage
 * - TextMessage
 * - UnsupportedMessage
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
  /**
   * Unique value that identifies the message object
   * Usually generated on the frontend side
   * */
  @Id
  private String id;

  /**
   * Parameter stored in MongoDB database that defines
   * unchangeable message author id
   */
  private String authorId;

  /**
   * Stores full message author info basing on authorId variable.
   * The parameter is set during the request because
   * just as author id is constant, the user object is not
   */
  @Transient
  private PublicUserModel author;

  /**
   * Stores message creation date
   */
  private Date createdAt;

  /**
   * Stores message update date
   */
  private Date updatedAt;

  /**
   * Contains custom metadata assigned to specific message
   */
  private Map<String, Object> metadata;

  /**
   * Defines the chat room where chat message is assigned to
   */
  private String roomId;

  /**
   * Defines message type.
   * Basing on this parameter we can call specific constructor
   * to build appropriate instance of Chat Message
   */
  private MessageType type;

  /**
   * Tells us if the message has been sent, received
   * or if it has any other status
   */
  private MessageStatus status;

  /**
   * Creates an appropriate ChatMessage instance basing on provided Map
   * @param json - Chat message as json
   * @param author - Details about message sender
   * @return Appropriate class that extends by ChatMessage
   */
  // TODO(dominik): Create .fromJson() constructor in ChatMessage child instances.
  //                Map [type] json parameter to call specific parameter - Much less code
  public static ChatMessage create(Map<String, Object> json, PublicUserModel author) {
    switch ((String) json.get("type")) {
      case "custom":
        CustomMessage customMessage = new CustomMessage();
        customMessage.setId((String) json.get("id"));
        customMessage.setType(MessageType.custom);
        customMessage.setAuthor(author);
        customMessage.setAuthorId(author.getId());
        customMessage.setMetadata((Map<String, Object>) json.get("metadata"));
        customMessage.setRoomId((String) json.get("roomId"));
        return customMessage;
      case "file":
        FileMessage fileMessage = new FileMessage();
        fileMessage.setId((String) json.get("id"));
        fileMessage.setType(MessageType.file);
        fileMessage.setAuthor(author);
        fileMessage.setAuthorId(author.getId());
        fileMessage.setMetadata((Map<String, Object>) json.get("metadata"));
        fileMessage.setRoomId((String) json.get("roomId"));
        fileMessage.setFileId((String) json.get("fileId"));
        return fileMessage;
      case "image":
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setId((String) json.get("id"));
        imageMessage.setType(MessageType.image);
        imageMessage.setAuthor(author);
        imageMessage.setAuthorId(author.getId());
        imageMessage.setMetadata((Map<String, Object>) json.get("metadata"));
        imageMessage.setRoomId((String) json.get("roomId"));
        imageMessage.setImageId((String) json.get("imageId"));
        return imageMessage;
      case "text":
        TextMessage textMessage = new TextMessage();
        textMessage.setId((String) json.get("id"));
        textMessage.setType(MessageType.text);
        textMessage.setAuthor(author);
        textMessage.setAuthorId(author.getId());
        textMessage.setMetadata((Map<String, Object>) json.get("metadata"));
        textMessage.setRoomId((String) json.get("roomId"));
        textMessage.setText((String) json.get("text"));
        return textMessage;
      default:
        UnsupportedMessage unsupportedMessage = new UnsupportedMessage();
        unsupportedMessage.setId((String) json.get("id"));
        unsupportedMessage.setType(MessageType.unsupported);
        unsupportedMessage.setAuthor(author);
        unsupportedMessage.setAuthorId(author.getId());
        unsupportedMessage.setMetadata((Map<String, Object>) json.get("metadata"));
        unsupportedMessage.setRoomId((String) json.get("roomId"));
        return unsupportedMessage;
    }
  }
}
