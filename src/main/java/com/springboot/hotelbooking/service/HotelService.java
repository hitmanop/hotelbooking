package com.springboot.hotelbooking.service;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.dto.HotelInfoDto;

public interface HotelService {

	HotelDto createHotel(HotelDto hotelDto);
	HotelDto getHotelById(long id);
	HotelDto updateHotelById(HotelDto hotelDto,long id);
	void deletehotelById(long id);
	HotelInfoDto getHotelInfoById(Long hotelId);

}
