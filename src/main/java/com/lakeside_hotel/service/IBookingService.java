package com.lakeside_hotel.service;

import java.util.List;

import com.lakeside_hotel.model.BookedRoom;

public interface IBookingService {

	void cancelBooking(Long bookingId);

	String saveBooking(Long roomId, BookedRoom bookingRequest);

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> getAllBookings();

	List<BookedRoom> getAllBookingByRoomId(Long roomId);
}
