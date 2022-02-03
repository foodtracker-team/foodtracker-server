package com.foodtracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
  @Value("${foodtracker.app.storage-absolute-path}")
  public String STORAGE_ABSOLUTE_PATH;

  @Value("${foodtracker.app.url}")
  public String appURL;

}
