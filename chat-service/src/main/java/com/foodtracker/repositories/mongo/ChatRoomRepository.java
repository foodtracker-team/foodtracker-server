package com.foodtracker.repositories.mongo;


import com.foodtracker.models.room.RoomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.foodtracker.models.room.ChatRoom;
import com.foodtracker.models.ERole;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

  @Query(value = "{ $and: [{type: ?0}, {usersId : {$all : [?1] }}]}")
  Optional<List<ChatRoom>> findByTypeAndUserId(RoomType type, String userId);

  Optional<List<ChatRoom>> findByChannelRoles(ERole role);
  @Query(value = "{ usersId : {$all : [?0] }}")
  Optional<List<ChatRoom>> findUserRooms(String userId);
}
