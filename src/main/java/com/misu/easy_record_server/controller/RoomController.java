package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.pojo.Room;
import com.misu.easy_record_server.pojo.RoomRecord;
import com.misu.easy_record_server.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseResult<Room> createRoom(@RequestBody Room room) {
        return ResponseResult.success(roomService.createRoom(room));
    }

    @PutMapping("/{id}")
    public ResponseResult<Room> updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        room.setId(id);
        return ResponseResult.success(roomService.updateRoom(room));
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseResult.success();
    }

    @GetMapping("/{id}")
    public ResponseResult<Room> getRoomById(@PathVariable Integer id) {
        return ResponseResult.success(roomService.getRoomById(id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseResult<Page<Room>> getRoomsByHotel(
            @PathVariable Integer hotelId,
            Pageable pageable) {
        return ResponseResult.success(roomService.getRoomsByHotelWithPage(hotelId, pageable));
    }

    @PostMapping("/checkIn")
    public ResponseResult<Void> checkIn(@RequestBody RoomRecord record) {
        roomService.checkIn(record);
        return ResponseResult.success();
    }

    @PostMapping("/checkOut/{roomId}")
    public ResponseResult<Void> checkOut(
            @PathVariable Integer roomId,
            @RequestParam(required = false) String remark) {
        roomService.checkOut(roomId, remark);
        return ResponseResult.success();
    }

    @PostMapping("/changeRoom")
    public ResponseResult<Void> changeRoom(
            @RequestParam Integer oldRoomId,
            @RequestParam Integer newRoomId,
            @RequestParam(required = false) String remark) {
        roomService.changeRoom(oldRoomId, newRoomId, remark);
        return ResponseResult.success();
    }

    @PutMapping("/{id}/status")
    public ResponseResult<Void> setRoomStatus(
            @PathVariable Integer id,
            @RequestParam Integer status,
            @RequestParam(required = false) String remark) {
        roomService.setRoomStatus(id, status, remark);
        return ResponseResult.success();
    }

    @GetMapping("/hotel/{hotelId}/available")
    public ResponseResult<List<Room>> getAvailableRooms(@PathVariable Integer hotelId) {
        return ResponseResult.success(roomService.getAvailableRooms(hotelId));
    }

    @GetMapping("/hotel/{hotelId}/status/{status}")
    public ResponseResult<List<Room>> getRoomsByStatus(
            @PathVariable Integer hotelId,
            @PathVariable Integer status) {
        return ResponseResult.success(roomService.getRoomsByStatus(hotelId, status));
    }

    @GetMapping("/{roomId}/currentRecord")
    public ResponseResult<RoomRecord> getCurrentRecord(@PathVariable Integer roomId) {
        return ResponseResult.success(roomService.getCurrentRecord(roomId));
    }

    @GetMapping("/{roomId}/records")
    public ResponseResult<List<RoomRecord>> getRoomRecords(@PathVariable Integer roomId) {
        return ResponseResult.success(roomService.getRoomRecords(roomId));
    }
}