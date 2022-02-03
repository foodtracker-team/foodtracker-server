package com.foodtracker.services;

import com.foodtracker.models.ERole;
import com.foodtracker.models.User;

import java.util.List;

public interface UserService {
  User getUserById(String id);
  List<User> getUsersByRole(List<ERole> roles);
}
