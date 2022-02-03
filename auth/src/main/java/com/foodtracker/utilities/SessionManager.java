package com.foodtracker.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.foodtracker.models.ERole;
import com.foodtracker.models.Role;
import com.foodtracker.models.User;
import com.foodtracker.repositories.jpa.UserRepository;
import com.foodtracker.security.services.UserDetailsImpl;

@Service
public class SessionManager {
  @Autowired
  UserRepository userRepository;

  public boolean containsRole(ERole searchedRole) {
    User user = getUser();
    for( Role role : user.getRoles() ) {
      if( role.getName() == searchedRole ) {
        return true;
      }
    }
    return false;
  }

  public boolean isAdmin() {
    User user = getUser();
    for( Role role : user.getRoles() ) {
      if( role.getName() == ERole.ROLE_ADMIN ) {
        return true;
      }
    }
    return false;
  }

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return getUserFromPrincipal((UserDetailsImpl) authentication.getPrincipal());

  }

  public User getUserFromPrincipal(UserDetailsImpl principal) {
    String id = principal.getId();
    return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
  }


}
