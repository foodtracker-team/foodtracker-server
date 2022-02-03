package com.foodtracker.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class Company {
  @Id
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "website")
  private String website;
  @Column(name = "phone")
  private String phone;
  @Column(name = "email")
  private String email;
  @Column(name = "timezone")
  private String timezone;
  @Column(name = "lang")
  private String lang;
  @Column(name = "avatar")
  private String avatar;
  @Column(name = "business_card")
  private String businessCard;
}
