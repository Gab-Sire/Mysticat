package com.multitiers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multitiers.domaine.User;

public interface UserRepository extends JpaRepository<User, String>{
	User findById(String id);
	User findByUsername(String id);
}
