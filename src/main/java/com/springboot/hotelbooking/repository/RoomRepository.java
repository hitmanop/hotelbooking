package com.springboot.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.hotelbooking.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

}
