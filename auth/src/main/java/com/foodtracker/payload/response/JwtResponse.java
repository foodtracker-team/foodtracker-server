package com.foodtracker.payload.response;

import com.foodtracker.models.BearerToken;
import com.foodtracker.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
	private BearerToken token;
	private String id;
	private String email;
	private String firstName;
	private String lastName;
	private String avatarId;
	private Set<Role> roles;

	public JwtResponse(String accessToken, String id, String email, String firstName, String avatarId,  String lastName,Set<Role> roles) {
		this.token = new BearerToken(accessToken);
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.avatarId = avatarId;
	}
}
