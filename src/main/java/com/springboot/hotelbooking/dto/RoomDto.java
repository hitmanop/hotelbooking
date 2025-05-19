package com.springboot.hotelbooking.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
	private List<String> amenities;
    private List<String> photos;
    private Integer totalCount;
    private Integer capacity;
}

