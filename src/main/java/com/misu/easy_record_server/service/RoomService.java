package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Room;
import com.misu.easy_record_server.pojo.RoomRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    // 房间基础操作
    Room createRoom(Room room);

    Room updateRoom(Room room);

    void deleteRoom(Integer id);

    Room getRoomById(Integer id);

    List<Room> getRoomsByHotel(Integer hotelId);

    Page<Room> getRoomsByHotelWithPage(Integer hotelId, Pageable pageable);

    // 房间状态操作
    void checkIn(RoomRecord record);

    void checkOut(Integer roomId, String remark);

    void changeRoom(Integer oldRoomId, Integer newRoomId, String remark);

    void setRoomStatus(Integer roomId, Integer status, String remark);

    // 房间查询
    List<Room> getAvailableRooms(Integer hotelId);

    List<Room> getRoomsByStatus(Integer hotelId, Integer status);

    RoomRecord getCurrentRecord(Integer roomId);

    List<RoomRecord> getRoomRecords(Integer roomId);
}