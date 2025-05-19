package com.springboot.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.hotelbooking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
