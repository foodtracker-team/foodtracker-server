package com.foodtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.tools.FileObject;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PublicUserModel {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String avatarId;
  private Set<Role> roles;

  public PublicUserModel(User user) {
    this.id = user.getId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.email = user.getEmail();
    this.roles = user.getRoles();
    if( user.getAvatar() != null ) {
      this.avatarId = user.getAvatar().getId();
    }
  }
}
