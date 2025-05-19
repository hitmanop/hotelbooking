package com.springboot.hotelbooking.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class HotelInfo {
	
    private String address;
    private String phoneNumber;
    private String email;
    private String location;

}
