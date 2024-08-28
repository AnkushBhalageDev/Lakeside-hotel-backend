package com.lakeside_hotel.controller;

import com.lakeside_hotel.model.Room;
import com.lakeside_hotel.response.RoomResponse;
import com.lakeside_hotel.service.IRoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
	@Autowired
    private IRoomService roomService;
    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,@RequestParam("roomType") String roomType, @RequestParam("roomPrice")BigDecimal roomPrice) throws SQLException, IOException {
       System.out.println(roomPrice);
       System.out.println(photo);
       System.out.println(roomType);
       
    	Room savedRoom= roomService.addNewRoom(photo,roomType,roomPrice);
        RoomResponse response= new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/room/types")
    public List<String> getRoomTypes(){
    	return roomService.getAllRoomTypes();
    }
}
