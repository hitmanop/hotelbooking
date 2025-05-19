package com.springboot.hotelbooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.dto.RoomDto;
import com.springboot.hotelbooking.entity.Hotel;
import com.springboot.hotelbooking.entity.Room;
import com.springboot.hotelbooking.exception.ResourceNotFoundException;
import com.springboot.hotelbooking.repository.HotelRepository;
import com.springboot.hotelbooking.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
	
	private final ModelMapper modelMapper;
	private final RoomRepository roomRepository;
	private final HotelRepository hotelRepository;
	private final InventoryService inventoryService;

	@Override
	@Transactional
	public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        Room room=modelMapper.map(roomDto, Room.class);
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(()->new RuntimeException("Not Found"));
        room.setHotel(hotel);
        room=roomRepository.save(room);
        
        if (hotel.getActive()) {
            inventoryService.createInventoryForMonth(room);
        }

        return modelMapper.map(room, RoomDto.class);
		
	}

	@Override
	public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
		Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(()->new RuntimeException("Not Found"));
		return hotel.getRoom().stream().map(entry->modelMapper.map(entry,RoomDto.class)).collect(Collectors.toList());	
	}

	@Override
	public RoomDto getRoomById(Long roomId) {
		Room room=roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Not Found"));
		return modelMapper.map(room, RoomDto.class);
		
	}

	@Override
	@Transactional
	public void deleteRoomById(Long roomId) {
		boolean exist=roomRepository.existsById(roomId);
		if(!exist) throw new RuntimeException("Not Found");
		Room room=roomRepository.findById(roomId).orElseThrow(()->new ResourceNotFoundException("Not Found"));
		inventoryService.deleteAllInventory(room);
		roomRepository.deleteById(roomId);
		
	}

}
