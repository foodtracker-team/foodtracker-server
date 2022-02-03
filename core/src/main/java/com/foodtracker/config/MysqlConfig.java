package com.foodtracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MysqlConfig {
  @Value("${spring.datasource.url}")
  private String databaseUrl;

  @Value("${spring.datasource.driver-class-name}")
  private String databaseDriver;

  @Value("${spring.datasource.username}")
  private String databaseUser;

  @Value("${spring.datasource.password}")
  private String databasePassword;


  @Bean
  public DataSource mysqlDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(databaseDriver);
    dataSource.setUrl(databaseUrl);
    dataSource.setUsername(databaseUser);
    dataSource.setPassword(databasePassword);
    return dataSource;
  }

}
