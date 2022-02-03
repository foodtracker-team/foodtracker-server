package com.foodtracker.models;


import com.foodtracker.models.FileObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(	name = "users",

		uniqueConstraints = {
			@UniqueConstraint(columnNames = "email") 
		})
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@OneToOne()
	@JoinColumn(name = "avatar_id", referencedColumnName = "id")
	private FileObject avatar;

	private String firstName;
	private String lastName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(	name = "company_users",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "company_id"))
  private Set<Company> companies = new HashSet<>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", roles=" + roles +
				'}';
	}

	public Map<String, Object> toPublicData() {
		Map<String, Object> publicUserData = new HashMap<>();
		publicUserData.put("firstName", firstName);
		publicUserData.put("lastName", lastName);
		return publicUserData;
	}
}
