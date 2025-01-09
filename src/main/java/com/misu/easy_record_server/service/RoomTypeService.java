package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.RoomType;
import java.util.List;

public interface RoomTypeService {
    RoomType createRoomType(RoomType roomType);

    RoomType updateRoomType(RoomType roomType);

    void deleteRoomType(Integer id);

    RoomType getRoomTypeById(Integer id);

    List<RoomType> getAllRoomTypesByHotel(Integer hotelId);

    List<RoomType> getActiveRoomTypesByHotel(Integer hotelId);
}