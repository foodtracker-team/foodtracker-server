package com.foodtracker.controllers;

import com.foodtracker.models.Company;
import com.foodtracker.models.User;
import com.foodtracker.repositories.jpa.UserRepository;
import com.foodtracker.security.services.UserDetailsImpl;
import com.foodtracker.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class CompanyController {
  @Autowired
  UserRepository userRepository;

  private CompanyService companyService;

  @Autowired
  public void setCompanyService(CompanyService companyService) {
    this.companyService = companyService;
  }


  @GetMapping("/company/user")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getMyCompanies() {
    User user = getUser();
    List<Company> companies = companyService.getMyCompanies(user);
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }

  @GetMapping("/company/user/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getUserCompanies(@PathVariable String id) {
    List<Company> companies = companyService.getUserCompanies(id);
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }

  @GetMapping("/company")
//  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getAllCompanies() {
    List<Company> companies = companyService.getAll();
    return new ResponseEntity<>(companies, HttpStatus.OK);
  }

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail();
    return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
  }
}
