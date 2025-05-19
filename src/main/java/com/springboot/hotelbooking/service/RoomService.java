package com.springboot.hotelbooking.service;

import java.util.List;

import com.springboot.hotelbooking.dto.RoomDto;

public interface RoomService {
	
	
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);
}
