package com.foodtracker.services;

import com.foodtracker.models.DriveFile;

import java.util.List;
import java.util.Map;

public interface DriveService {
    /**
     * Finds files from /static/*
     * @return - List of DriveFiles
     */
    List<DriveFile> findFilesInRootDirectory();

    /**
     * Finds files from provided directory
     * @return - List of DriveFiles
     */
    List<DriveFile> getFilesInDirectory(String directory);
}
