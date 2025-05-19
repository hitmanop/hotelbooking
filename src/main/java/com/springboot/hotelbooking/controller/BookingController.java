package com.springboot.hotelbooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hotelbooking.dto.BookingDto;
import com.springboot.hotelbooking.dto.BookingRequest;
import com.springboot.hotelbooking.dto.GuestDto;
import com.springboot.hotelbooking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.intiliseBooking(bookingRequest));
    }
    
    @PostMapping("{bookingId}/addGuest")
    public ResponseEntity<BookingDto> addGuest(@PathVariable long bookingId , @RequestBody List<GuestDto> guestList){
    	
    	return ResponseEntity.ok(bookingService.addGuest(bookingId,guestList));
    	
    }	
    
	

}
