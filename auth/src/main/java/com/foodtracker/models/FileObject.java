package com.foodtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class FileObject {
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

  @Column(name = "extension")
  private String extension;

  @OneToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "upload_date", nullable=true)
  private Date timestamp;

  @PrePersist
  protected void onCreate() {
    timestamp = new Date();
  }

  public FileObject(String id) {
    this.id = id;
  }

  public FileObject(String name, String extension, User user) {
    this.id = id;
    this.name = name;
    this.extension = extension;
    this.user = user;
    this.timestamp = timestamp;
  }

  public String getParsedName() {
    return id + "." + extension;
  }

  public boolean isImage() {
    List<String> imageExtensions = List.of(new String[]{"png", "jpg", "jpeg"});
    return imageExtensions.contains(extension);
  }
}


