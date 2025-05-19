package com.springboot.hotelbooking.dto;

import java.util.List;

import com.springboot.hotelbooking.entity.HotelInfo;

import lombok.Data;
@Data
public class HotelDto {

	private Long id;
	private String name;
	private String city;
	private List<String> amenities;
    private List<String> photos;
	private HotelInfo contactInfo;
	private Boolean active;
	
}
