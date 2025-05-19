package com.springboot.hotelbooking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelInfoDto {
	
	   private HotelDto hotel;
	    private List<RoomDto> rooms;

}
