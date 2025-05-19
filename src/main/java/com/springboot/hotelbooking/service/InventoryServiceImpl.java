package com.springboot.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.dto.HotelSearchRequest;
import com.springboot.hotelbooking.entity.Hotel;
import com.springboot.hotelbooking.entity.Inventory;
import com.springboot.hotelbooking.entity.Room;
import com.springboot.hotelbooking.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
	
	private final InventoryRepository inventoryRepository;
	 private final ModelMapper modelMapper;

	@Override
	public void createInventoryForMonth(Room room) {
		
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusMonths(1);
        for(;!today.isAfter(endDate);today=today.plusDays(1)) {
        	Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }		
	}

	@Override
	public void deleteAllInventory(Room room) {
		inventoryRepository.deleteByRoom(room);
		
	}

	@Override
	public Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
	 
		Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(), hotelSearchRequest.getSize());
		
        long dateCount =
                ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate()) + 1;

        Page<Hotel> hotelPage = inventoryRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate(), hotelSearchRequest.getRoomsCount(),
                dateCount, pageable);

        return hotelPage.map((element) -> modelMapper.map(element, HotelDto.class));
		
		
		
	}

	
	
	

}
