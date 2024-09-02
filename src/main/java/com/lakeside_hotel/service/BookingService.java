package com.lakeside_hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lakeside_hotel.exception.InvalidBookngRequestException;
import com.lakeside_hotel.model.BookedRoom;
import com.lakeside_hotel.model.Room;
import com.lakeside_hotel.repository.BookingRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
	private final BookingRepository bookingRpository;
	private final IRoomService roomService;

	@Override
	public List<BookedRoom> getAllBookingByRoomId(Long roomId) {

		return bookingRpository.findByRoomId(roomId);
	}

	@Override
	public void cancelBooking(Long bookingId) {
		bookingRpository.deleteById(bookingId);
	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookngRequestException("Check in date must come before check out date");
		}
		Room room = roomService.getRoomById(roomId).get();
		List<BookedRoom> existingBookings = room.getBookings();
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
		if(roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRpository.save(bookingRequest);
		}else {
			throw new InvalidBookngRequestException(" Sorry ! This room is not avalable for selected dates");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		
		return bookingRpository.findByBookingConfirmationCode(confirmationCode);
	}

	@Override
	public List<BookedRoom> getAllBookings() {
		return bookingRpository.findAll();
	}

	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {

		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
						|| bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
	}

}
