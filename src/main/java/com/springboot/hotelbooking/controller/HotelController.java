package com.springboot.hotelbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.service.HotelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/admin/hotels")
@RequiredArgsConstructor
public class HotelController {
	
	
	private final HotelService hotelService;
	
    @PostMapping
	ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto){
		HotelDto hotel=hotelService.createHotel(hotelDto);
		return new ResponseEntity<>(hotel,HttpStatus.CREATED);
		
	}
    @GetMapping("/{id}")
	ResponseEntity<HotelDto> createHotel(@PathVariable long id){
		HotelDto hotel=hotelService.getHotelById(id);
		return new ResponseEntity<>(hotel,HttpStatus.OK);
		
	}
	
    @PutMapping("{id}")
    ResponseEntity<HotelDto> updateHotel(@RequestBody HotelDto hotelDto, @PathVariable long id){
    	HotelDto hotel=hotelService.updateHotelById(hotelDto, id);
    	return new ResponseEntity<>(hotel,HttpStatus.OK);
  
    }
    @DeleteMapping("/{id}")
    ResponseEntity<HotelDto> updateHotel(@PathVariable long id){
    	hotelService.deletehotelById(id);
    	return new ResponseEntity<>(HttpStatus.OK);
  
    }

}
