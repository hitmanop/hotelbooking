package com.springboot.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.dto.BookingDto;
import com.springboot.hotelbooking.dto.BookingRequest;
import com.springboot.hotelbooking.dto.GuestDto;
import com.springboot.hotelbooking.entity.Booking;
import com.springboot.hotelbooking.entity.Guest;
import com.springboot.hotelbooking.entity.Hotel;
import com.springboot.hotelbooking.entity.Inventory;
import com.springboot.hotelbooking.entity.Room;
import com.springboot.hotelbooking.entity.User;
import com.springboot.hotelbooking.entity.enums.BookingStatus;
import com.springboot.hotelbooking.exception.ResourceNotFoundException;
import com.springboot.hotelbooking.exception.UnAuthorisedException;
import com.springboot.hotelbooking.repository.BookingRepository;
import com.springboot.hotelbooking.repository.GuestRepository;
import com.springboot.hotelbooking.repository.HotelRepository;
import com.springboot.hotelbooking.repository.InventoryRepository;
import com.springboot.hotelbooking.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final HotelRepository hotelRepository;
	private final RoomRepository roomRepository;
	private final InventoryRepository inventoryRepository;
    private final GuestRepository guestRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public BookingDto intiliseBooking(BookingRequest bookingRequest) {

		Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(
				() -> new ResourceNotFoundException("Hotel not found with id: " + bookingRequest.getHotelId()));

		Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(
				() -> new ResourceNotFoundException("Room not found with id: " + bookingRequest.getRoomId()));

		List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
				bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomsCount());

		long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate()) + 1;

		if (inventoryList.size() != daysCount) {
			throw new IllegalStateException("Room is not available anymore");
		}

		for (Inventory list : inventoryList) {
			list.setReservedCount(list.getReservedCount() + bookingRequest.getRoomsCount());
		}
		inventoryRepository.saveAll(inventoryList);

		Booking booking = Booking.builder().hotel(hotel).room(room).bookingStatus(BookingStatus.RESERVED)
				.checkInDate(bookingRequest.getCheckInDate()).checkOutDate(bookingRequest.getCheckOutDate())
				.user(getCurrentUser()).roomsCount(bookingRequest.getRoomsCount()).amount(BigDecimal.TEN).build();

		booking = bookingRepository.save(booking);
		return modelMapper.map(booking, BookingDto.class);

	}

	private User getCurrentUser() {
		 return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
    public boolean hasBookingExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

	@Override
	public BookingDto addGuest(long bookingId, List<GuestDto> guestList) {

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        User user = getCurrentUser();

        if (!user.equals(booking.getUser())) {
            throw new UnAuthorisedException("Booking does not belong to this user with id: "+user.getId());
        }
		
		if(hasBookingExpired(booking)) {
			throw new IllegalStateException("Booking has already expired");
		}
        if(booking.getBookingStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }
      
        for(GuestDto guestDto:guestList) {
        	Guest guests = modelMapper.map(guestDto, Guest.class);
        	guests.setUser(user);
            guests = guestRepository.save(guests);
            booking.getGuests().add(guests);   	
        }

        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
		
		
	}

}
