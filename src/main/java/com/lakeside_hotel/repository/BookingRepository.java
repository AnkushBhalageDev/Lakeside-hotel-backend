package com.lakeside_hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lakeside_hotel.model.BookedRoom;

public interface BookingRepository extends JpaRepository<BookedRoom, Long>{

	
	List<BookedRoom> findByRoomId(Long roomId);

	
	BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
