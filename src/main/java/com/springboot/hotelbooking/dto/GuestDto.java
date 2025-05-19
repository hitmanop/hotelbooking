package com.springboot.hotelbooking.dto;

import com.springboot.hotelbooking.entity.enums.Gender;

import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
}

