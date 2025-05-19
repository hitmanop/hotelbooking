package com.springboot.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.hotelbooking.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long>{

}
