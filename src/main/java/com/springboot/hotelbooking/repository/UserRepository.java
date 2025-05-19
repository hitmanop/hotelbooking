package com.springboot.hotelbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.hotelbooking.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
    Optional<User> findByEmail(String email);

}
