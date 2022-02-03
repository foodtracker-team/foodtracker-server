package com.foodtracker.services.impl;

import com.foodtracker.models.DriveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodtracker.AppConfig;
import com.foodtracker.models.Company;
import com.foodtracker.models.User;
import com.foodtracker.services.DriveService;
import com.foodtracker.utilities.SessionManager;

import java.io.File;
import java.util.*;

@Service
public class DriveServiceImpl implements DriveService {
  @Autowired
  AppConfig appConfig;

  @Autowired
  SessionManager sessionManager;

  @Override
  public List<DriveFile> findFilesInRootDirectory() {
    User user = sessionManager.getUser();
    List<DriveFile> result = new ArrayList<>();
    if( sessionManager.isAdmin() ) {
      result = getFilesInDirectory("");
    } else {
      for (Company company : user.getCompanies()) {
        DriveFile driveFile = new DriveFile();
        driveFile.setName(company.getName());
        driveFile.setPath("company/" + company.getId());
        driveFile.setIsDirectory(true);
        driveFile.setIsFile(false);
        driveFile.setLastModified(Long.parseLong("0"));
        result.add(driveFile);
      }
    }
    return result;
  }

  @Override
  public List<DriveFile> getFilesInDirectory(String directory) {
    List<DriveFile> parsedFiles = new ArrayList<>();
    try {
      final File folder = new File(appConfig.STORAGE_ABSOLUTE_PATH + "/" + directory);
      List<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));

      for (File file : files) {
        DriveFile driveFile = new DriveFile();
        driveFile.setName(file.getName());
        driveFile.setPath( file.getName());
        driveFile.setIsDirectory( file.isDirectory());
        driveFile.setIsFile(file.isFile());
        driveFile.setLastModified(file.lastModified());
        parsedFiles.add(driveFile);
      }

    } catch (Exception e) {
      // do nothing
    }
    return parsedFiles;
  }


}
