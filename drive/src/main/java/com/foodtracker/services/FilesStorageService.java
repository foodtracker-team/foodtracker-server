package com.foodtracker.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.foodtracker.models.FileObject;
import com.foodtracker.models.ResourceResponse;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  /**
   * Saves the provided file in the storage and database
   * @param file - Multipart file from request
   * @return FileObject contains all file details
   */
  FileObject save(MultipartFile file);

  /**
   * Returns file by provided file id
   * @param fileId - file ID
   * @param width - Optional image width
   * @param height - Optional image height
   * @return file as resource
   */
  ResourceResponse load(String fileId, Integer width, Integer height);
}