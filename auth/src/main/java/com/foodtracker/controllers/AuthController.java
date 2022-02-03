package com.foodtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.foodtracker.models.ERole;
import com.foodtracker.models.Role;
import com.foodtracker.models.User;
import com.foodtracker.payload.request.LoginRequest;
import com.foodtracker.payload.request.SignupRequest;
import com.foodtracker.payload.response.JwtResponse;
import com.foodtracker.repositories.jpa.RoleRepository;
import com.foodtracker.repositories.jpa.UserRepository;
import com.foodtracker.security.jwt.JwtUtils;
import com.foodtracker.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
  AuthenticationManager authenticationManager;

	@Autowired
  UserRepository userRepository;

	@Autowired
  RoleRepository roleRepository;

	@Autowired
  PasswordEncoder encoder;

	@Autowired
  JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

    User user = getUser();

		String avatarId = null;
		if( user.getAvatar() != null ) {
			avatarId = user.getAvatar().getId();
		}
		return ResponseEntity.ok(new JwtResponse(jwt,
      user.getId(),
      user.getEmail(),
      user.getFirstName(),
			avatarId,
      user.getLastName(),
      user.getRoles()
			));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(user.getId(), HttpStatus.OK);
	}

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();
    return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
  }
}
