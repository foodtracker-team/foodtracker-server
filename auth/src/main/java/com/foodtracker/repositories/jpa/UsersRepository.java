package com.foodtracker.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.foodtracker.models.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {
	List<User> findByEmailContaining(String email);
}
