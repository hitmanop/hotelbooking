package com.springboot.hotelbooking.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.dto.HotelInfoDto;
import com.springboot.hotelbooking.dto.HotelSearchRequest;
import com.springboot.hotelbooking.service.HotelService;
import com.springboot.hotelbooking.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelSearchController {
	
    private final InventoryService inventoryService;
    private final HotelService hotelService;
	
    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest) {

        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> GetHotelInfo(@PathVariable long hotelId) {
    return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
	

}
