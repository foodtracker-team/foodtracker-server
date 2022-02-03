package com.foodtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.foodtracker.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.foodtracker.repositories.mongo")
public class FoodtrackerApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(FoodtrackerApplication.class);
    app.run(args);
  }

}
