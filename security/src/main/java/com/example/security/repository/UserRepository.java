package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
}
