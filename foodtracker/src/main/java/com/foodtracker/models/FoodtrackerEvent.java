package com.foodtracker.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "foodtracker_event")
public class FoodtrackerEvent {
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
  @Column(name = "date_start")
  private String dateStart;
  @Column(name = "date_end")
  private String dateEnd;
  @Column(name = "profile_image")
  private String profileImage;
  @Column(name = "background_image")
  private String backgroundImage;

  public FoodtrackerEvent() {
  }

  public FoodtrackerEvent(String id, String name, String description, String dateStart, String dateEnd, String profileImage, String backgroundImage) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.profileImage = profileImage;
    this.backgroundImage = backgroundImage;
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

  public String getDateStart() {
    return dateStart;
  }

  public void setDateStart(String dateStart) {
    this.dateStart = dateStart;
  }

  public String getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getBackgroundImage() {
    return backgroundImage;
  }

  public void setBackgroundImage(String backgroundImage) {
    this.backgroundImage = backgroundImage;
  }
}


