package com.foodtracker.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "foodtrucks")
public class FoodTruck {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
    name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
  private String id;
  @Column(name = "name")
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "tags")
  private String tags;

  public FoodTruck(String id, String name, String description, String tags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.tags = tags;
  }

  public FoodTruck() {
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "FoodTruck{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      '}';
  }
}


