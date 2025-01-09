package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.pojo.RoomType;
import com.misu.easy_record_server.service.RoomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roomTypes")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping
    public ResponseResult<RoomType> createRoomType(@RequestBody RoomType roomType) {
        return ResponseResult.success(roomTypeService.createRoomType(roomType));
    }

    @PutMapping("/{id}")
    public ResponseResult<RoomType> updateRoomType(@PathVariable Integer id, @RequestBody RoomType roomType) {
        roomType.setId(id);
        return ResponseResult.success(roomTypeService.updateRoomType(roomType));
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteRoomType(@PathVariable Integer id) {
        roomTypeService.deleteRoomType(id);
        return ResponseResult.success();
    }

    @GetMapping("/{id}")
    public ResponseResult<RoomType> getRoomTypeById(@PathVariable Integer id) {
        return ResponseResult.success(roomTypeService.getRoomTypeById(id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseResult<List<RoomType>> getRoomTypesByHotel(@PathVariable Integer hotelId) {
        return ResponseResult.success(roomTypeService.getAllRoomTypesByHotel(hotelId));
    }

    @GetMapping("/hotel/{hotelId}/active")
    public ResponseResult<List<RoomType>> getActiveRoomTypesByHotel(@PathVariable Integer hotelId) {
        return ResponseResult.success(roomTypeService.getActiveRoomTypesByHotel(hotelId));
    }
}