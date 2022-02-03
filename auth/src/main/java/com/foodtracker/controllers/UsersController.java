package com.foodtracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtracker.models.FileObject;
import com.foodtracker.models.PublicUserModel;
import com.foodtracker.models.Role;
import com.foodtracker.models.User;
import com.foodtracker.repositories.jpa.FilesRepository;
import com.foodtracker.repositories.jpa.RoleRepository;
import com.foodtracker.repositories.jpa.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class UsersController {

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserController userController;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  FilesRepository filesRepository;

  @GetMapping("/users")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<PublicUserModel>> getAllUsers(@RequestParam(required = false) String name, @RequestParam() int page) {
    try {
      List<User> users = new ArrayList<>();

      if (name == null)
        users.addAll(usersRepository.findAll());
      else
        users.addAll(usersRepository.findByEmailContaining(name));

      int elementsOnPage = 20;
      int pageNumber = page * elementsOnPage;
      if (pageNumber > users.size()) {
        if (pageNumber + elementsOnPage < users.size()) {
          users = users.subList(pageNumber, users.size() - 1);
        } else {
          users = users.subList(pageNumber, pageNumber + elementsOnPage - 1);
        }
      }
      List<PublicUserModel> publicUserModel = new ArrayList<>();
      for (User user : users) {
        publicUserModel.add(new PublicUserModel(user));
      }

      return new ResponseEntity<>(publicUserModel, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/users/{id}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
    Optional<User> userData = usersRepository.findById(id);
    System.out.println("USER DATA: " + userData);
    return userData.map(user -> new ResponseEntity<>(new PublicUserModel(user), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/users/{id}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody Map<String, Object> newUserData) {
    System.out.println("newUserData: " + newUserData);
    Optional<User> user = usersRepository.findById(id);

    if (user.isPresent()) {
      User fullUser = user.get();
      if (newUserData.get("firstName") != null) {
        fullUser.setFirstName((String) newUserData.get("firstName"));
      }
      if (newUserData.get("lastName") != null) {
        fullUser.setLastName((String) newUserData.get("lastName"));
      }
      if (newUserData.get("email") != null) {
        fullUser.setEmail((String) newUserData.get("email"));
      }
      if (newUserData.get("roles") != null) {
        Set<Role> roles = new HashSet<>();
        for (Map<String, Object> roleData : (List<Map<String, Object>>) newUserData.get("roles")) {
          final ObjectMapper mapper = new ObjectMapper();
          final Role role = mapper.convertValue(roleData, Role.class);
          roles.add(role);
        }
        fullUser.setRoles(roles);
      }
      if (newUserData.get("password") != null) {
        fullUser.setPassword(encoder.encode((String) newUserData.get("password")));
      }
      if (newUserData.get("avatarId") != null) {
        FileObject avatar = filesRepository.findById((String) newUserData.get("avatarId")).get();
        fullUser.setAvatar(avatar);
      }

      return new ResponseEntity<>(new PublicUserModel(usersRepository.save(fullUser)), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/usersPassword/{id}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> updateUserPassword(@PathVariable("id") String id, @RequestBody User user) {
    Optional<User> userData = usersRepository.findById(id);

    if (userData.isPresent()) {
      User _user = userData.get();
      _user.setPassword(encoder.encode(user.getPassword()));

      return new ResponseEntity<>(usersRepository.save(_user), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/users/{id}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
    try {
      usersRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  @GetMapping("/roles")
//  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<Role>> getRoles() {
    try {
      List<Role> roles = roleRepository.findAll();
      return new ResponseEntity<>(roles, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
