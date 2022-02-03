package com.foodtracker.services.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import com.foodtracker.AppConfig;
import com.foodtracker.models.FileObject;
import com.foodtracker.models.ResourceResponse;
import com.foodtracker.models.User;
import com.foodtracker.repositories.jpa.FilesRepository;
import com.foodtracker.services.FilesStorageService;
import com.foodtracker.utilities.BufferedImageUtils;
import com.foodtracker.utilities.SessionManager;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  @Autowired
  AppConfig appConfig;

  @Autowired
  FilesRepository filesRepository;

  @Autowired
  SessionManager sessionManager;

  private final Path root = Paths.get("/media/dominik/dane/github/Foodtracker/foodtracker-server/static/uploads");

  @Override
  public FileObject save(MultipartFile file) {
    try {
      String fileName = file.getOriginalFilename();
      System.out.println(fileName);
      String extension = fileName.split("\\.")[fileName.split("\\.").length-1];
      User user = sessionManager.getUser();
      FileObject fileObject = new FileObject(fileName, extension, user);
      fileObject = filesRepository.save(fileObject);
      Files.copy(file.getInputStream(), this.root.resolve(fileObject.getId() + "." + fileObject.getExtension()));
      return fileObject;
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public ResourceResponse load(String fileId, Integer width, Integer height) {
    try {
      Optional<FileObject> fileObjectOptional = filesRepository.findById(fileId);
      if( fileObjectOptional.isPresent() ) {
        FileObject fileObject = fileObjectOptional.get();
        System.out.println(appConfig.STORAGE_ABSOLUTE_PATH + "/uploads/" +fileObject.getParsedName());
        Path file = root.resolve(appConfig.STORAGE_ABSOLUTE_PATH + "/uploads/" +fileObject.getParsedName());
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
          if( fileObject.isImage() && width != null && height != null ) {
            BufferedImage originalImage = ImageIO.read(new File(file.toUri()));
            BufferedImage outputImage = BufferedImageUtils.resize(originalImage, width, height);
            ResourceResponse resourceResponse = new ResourceResponse();
            resourceResponse.setFileName(fileObject.getName());
            resourceResponse.setResource(BufferedImageUtils.toResource(outputImage, fileObject.getExtension()));
            return resourceResponse;
          }

          ResourceResponse resourceResponse = new ResourceResponse();
          resourceResponse.setFileName(fileObject.getName());
          resourceResponse.setResource(resource);
          return resourceResponse;
        } else {
          throw new RuntimeException("Could not read the file!");
        }
      } else {
        throw new RuntimeException("File not exists in database!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}