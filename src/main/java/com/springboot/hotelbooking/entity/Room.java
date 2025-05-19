package com.springboot.hotelbooking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.springboot.hotelbooking.util.ListToJsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;

	@Column(nullable = false)
	private String type;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal basePrice;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
	private List<String> amenities;
	
    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListToJsonConverter.class)
    private List<String> photos;

	@Column(nullable = false)
	private Integer totalCount;

	@Column(nullable = false)
	private Integer capacity;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
