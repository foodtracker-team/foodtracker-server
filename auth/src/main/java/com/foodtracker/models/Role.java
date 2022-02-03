package com.foodtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return name == role.name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}