package com.springboot.hotelbooking.service;

import java.util.List;

import com.springboot.hotelbooking.dto.BookingDto;
import com.springboot.hotelbooking.dto.BookingRequest;
import com.springboot.hotelbooking.dto.GuestDto;

public interface BookingService {
	
	public BookingDto intiliseBooking(BookingRequest bookingRequest);

	public BookingDto addGuest(long bookingId , List<GuestDto> guestList);

}
