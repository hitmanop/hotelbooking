package com.springboot.hotelbooking.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.dto.HotelDto;
import com.springboot.hotelbooking.dto.HotelInfoDto;
import com.springboot.hotelbooking.dto.RoomDto;
import com.springboot.hotelbooking.entity.Hotel;
import com.springboot.hotelbooking.exception.ResourceNotFoundException;
import com.springboot.hotelbooking.repository.HotelRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
	
	
	private final HotelRepository hotelRepository;
	private final ModelMapper modelMapper;

	@Override
	public HotelDto createHotel(HotelDto hotelDto) {
		
		Hotel hotel=modelMapper.map(hotelDto, Hotel.class);
			hotel= hotelRepository.save(hotel);
			return modelMapper.map(hotel, HotelDto.class);
	}

	@Override
	public HotelDto getHotelById(long id) {
		Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found"));
		return modelMapper.map(hotel, HotelDto.class);
	}

	@Override
	public HotelDto updateHotelById(HotelDto hotelDto, long id) {
		Hotel hotel=hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found"));
		modelMapper.map(hotelDto, hotel);
		hotel.setId(id);
		hotelRepository.save(hotel);
		return modelMapper.map(hotel, HotelDto.class);
	}

	@Override
	public void deletehotelById(long id) {
		boolean exist=hotelRepository.existsById(id);
		if(!exist) throw new RuntimeException("Not Found");
		hotelRepository.deleteById(id);
	}

	@Override
	public HotelInfoDto getHotelInfoById(Long hotelId) {
		
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRoom()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
	}


}
