package com.expensetracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// new jpa finder method for email
	Optional<User> findByEmail(String email);

}
