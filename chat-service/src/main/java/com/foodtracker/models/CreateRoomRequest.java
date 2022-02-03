package com.foodtracker.models;

import java.util.List;

public class CreateRoomRequest {
  List<String> users;

  public CreateRoomRequest(List<String> users) {
    this.users = users;
  }

  public CreateRoomRequest() {
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }
}
