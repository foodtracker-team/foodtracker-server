package com.foodtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BearerToken {
  final String type = "Bearer";
  private String token;

  public BearerToken(String token) {
    this.token = token;
  }
}
