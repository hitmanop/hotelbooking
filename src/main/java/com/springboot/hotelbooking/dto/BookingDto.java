package com.springboot.hotelbooking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.springboot.hotelbooking.entity.enums.BookingStatus;

import lombok.Data;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
	
}
