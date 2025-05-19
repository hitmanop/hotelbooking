package com.springboot.hotelbooking.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.springboot.hotelbooking.util.ListToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String city;
	
    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
	private List<String> amenities;
	
    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> photos;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Embedded
	private HotelInfo contactInfo;

	@Column(nullable = false)
	private Boolean active;
	
	@OneToMany(mappedBy = "hotel")
	private List<Room> room;

}
