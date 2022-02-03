package com.foodtracker.controllers;

import com.foodtracker.AppConfig;
import com.foodtracker.models.FileObject;
import com.foodtracker.models.ResourceResponse;
import com.foodtracker.models.ResponseMessage;
import com.foodtracker.services.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api/drive/")
public class FilesController {
  @Autowired
  AppConfig appConfig;

  private FilesStorageService storageService;

  @Autowired
  public void setStorageService(FilesStorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      FileObject uploadedFile = storageService.save(file);
      return new ResponseEntity<>(uploadedFile, HttpStatus.OK);
    } catch (Exception e) {
      String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<InputStreamSource> getFile(@PathVariable String filename, @RequestParam(required=false) Integer width, @RequestParam(required=false) Integer height) {
    ResourceResponse file = storageService.load(filename, width, height);
    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"").body(file.getResource());
  }
}