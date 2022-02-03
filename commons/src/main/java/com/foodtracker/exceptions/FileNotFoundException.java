package com.foodtracker.exceptions;

public class FileNotFoundException extends RuntimeException {
  public FileNotFoundException(String alert) {
    super(alert);
  }
}