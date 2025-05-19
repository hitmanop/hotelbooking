package com.springboot.hotelbooking.service;

import org.springframework.data.domain.Page;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.dto.HotelSearchRequest;
import com.springboot.hotelbooking.entity.Room;

public interface InventoryService {
	
	void createInventoryForMonth(Room room);
	void deleteAllInventory(Room room);
	Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);

}
