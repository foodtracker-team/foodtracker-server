package com.foodtracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodtracker.models.FileObject;
import com.foodtracker.models.Role;
import com.foodtracker.repositories.jpa.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.foodtracker.models.User;
import com.foodtracker.repositories.jpa.RoleRepository;
import com.foodtracker.repositories.jpa.UsersRepository;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
  UsersRepository usersRepository;

	@Autowired
  RoleRepository roleRepository;

  @Autowired
  FilesRepository filesRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping()
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> updateUser(@RequestBody Map<String, Object> newUserData) {
    System.out.println("newUserData: " + newUserData);
    User fullUser = null;
	  if(newUserData.get("id") != null) {
      Optional<User> userData = usersRepository.findById((String) newUserData.get("id"));
      if (userData.isPresent()) {
        fullUser = userData.get();
      }
    } else {
	    fullUser = new User();
    }
		if (fullUser != null) {
			if( newUserData.get("firstName") != null ) {
			  fullUser.setFirstName((String) newUserData.get("firstName"));
      }
      if( newUserData.get("lastName") != null ) {
        fullUser.setLastName((String) newUserData.get("lastName"));
      }
      if( newUserData.get("email") != null ) {
        fullUser.setEmail((String) newUserData.get("email"));
      }
      if( newUserData.get("roles") != null ) {
        Set<Role> roles = new HashSet<>();
        for( Map<String, Object> roleData : (List<Map<String, Object>>) newUserData.get("roles")) {
          final ObjectMapper mapper = new ObjectMapper();
          final Role role = mapper.convertValue(roleData, Role.class);
          roles.add(role);
        }
        fullUser.setRoles(roles);
      }
      if( newUserData.get("password") != null ) {
        fullUser.setPassword(encoder.encode((String) newUserData.get("password")));
      }
      if( newUserData.get("avatarId") != null ) {
        fullUser.setAvatar(new FileObject((String) newUserData.get("avatarId")));
      }
			return new ResponseEntity<>(usersRepository.save(fullUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
