package com.foodtracker.controllers;

import com.foodtracker.models.DriveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.foodtracker.services.DriveService;
import com.foodtracker.utilities.SessionManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/drive/")
public class DriveController {

  private DriveService driveService;

  @Autowired
  SessionManager sessionManager;

  @Autowired
  public void setDriveService(DriveService driveService) {
    this.driveService = driveService;
  }


  @PreAuthorize("hasRole('USER')")
  @GetMapping({"/getFiles"})
  public ResponseEntity<?> getFiles() {
    List<DriveFile> result = driveService.findFilesInRootDirectory();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping({"/getFiles/**"})
  public ResponseEntity<?> getFiles(HttpServletRequest request) {
    String requestURL = request.getRequestURL().toString();
    String moduleName = requestURL.split("/getFiles/")[1];
    if( !sessionManager.isAdmin() && !moduleName.contains("company")) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    List<DriveFile> result = driveService.getFilesInDirectory(moduleName);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
