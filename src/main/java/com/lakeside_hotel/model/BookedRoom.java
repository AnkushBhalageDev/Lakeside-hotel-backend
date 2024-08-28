package com.lakeside_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name="check_In")
    private LocalDate checkInDate;
    @Column(name="check_Out")
    private LocalDate checkOutDate;
    @Column(name="guest_FullName")
    private String guestFullName;
    @Column(name="guest_Email")
    private String guestEmail;
    @Column(name="adults")
    private int NoOfAdults;
    @Column(name="children")
    private int NoOfChildren;
    @Column(name="total_guest")
    private int totalNoOfGuest;
    @Column(name="confirmation_Code")
    private String bookingConfirmationCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    public void calculateTotalNumberOfGuest() {
        this.totalNoOfGuest = this.NoOfAdults + this.NoOfChildren;
    }

    public void setNoOfAdults(int noOfAdults) {
        NoOfAdults = noOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNoOfChildren(int noOfChildren) {
        NoOfChildren = noOfChildren;
        calculateTotalNumberOfGuest();
    }

    public BookedRoom(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
