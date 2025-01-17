package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Room;
import com.misu.easy_record_server.pojo.RoomRecord;
import com.misu.easy_record_server.repository.RoomRecordRepository;
import com.misu.easy_record_server.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomServiceImplementation implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomRecordRepository recordRepository;

    public RoomServiceImplementation(RoomRepository roomRepository,
            RoomRecordRepository recordRepository) {
        this.roomRepository = roomRepository;
        this.recordRepository = recordRepository;
    }

    @Override
    @Transactional
    public Room createRoom(Room room) {
        room.setCreateTime(LocalDateTime.now());
        room.setUpdateTime(LocalDateTime.now());
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room updateRoom(Room room) {
        room.setUpdateTime(LocalDateTime.now());
        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public void deleteRoom(Integer id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room getRoomById(Integer id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Room> getRoomsByHotel(Integer hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    public Page<Room> getRoomsByHotelWithPage(Integer hotelId, Pageable pageable) {
        return roomRepository.findByHotelId(hotelId, pageable);
    }

    @Override
    @Transactional
    public void checkIn(RoomRecord record) {
        // 检查房间是否可用
        Room room = roomRepository.findById(record.getRoomId())
                .orElseThrow(() -> new RuntimeException("房间不存在"));
        if (room.getStatus() != 0) {
            throw new RuntimeException("房间不可入住");
        }

        // 更新房间状态
        room.setStatus(1);
        room.setUpdateTime(LocalDateTime.now());
        roomRepository.save(room);

        // 创建入住记录
        record.setCheckInTime(LocalDateTime.now());
        record.setStatus(1);
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        recordRepository.save(record);
    }

    @Override
    @Transactional
    public void checkOut(Integer roomId, String remark) {
        // 更新房间状态
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("房间不存在"));
        room.setStatus(2); // 设置为待打扫状态
        room.setUpdateTime(LocalDateTime.now());
        roomRepository.save(room);

        // 更新入住记录
        RoomRecord record = recordRepository.findByRoomIdAndStatus(roomId, 1)
                .orElseThrow(() -> new RuntimeException("未找到在住记录"));
        record.setCheckOutTime(LocalDateTime.now());
        record.setStatus(0);
        record.setRemark(remark);
        record.setUpdateTime(LocalDateTime.now());
        recordRepository.save(record);
    }

    @Override
    @Transactional
    public void changeRoom(Integer oldRoomId, Integer newRoomId, String remark) {
        // 检查新房间是否可用
        Room newRoom = roomRepository.findById(newRoomId)
                .orElseThrow(() -> new RuntimeException("新房间不存在"));
        if (newRoom.getStatus() != 0) {
            throw new RuntimeException("新房间不可入住");
        }

        // 获取当前入住记录
        RoomRecord record = recordRepository.findByRoomIdAndStatus(oldRoomId, 1)
                .orElseThrow(() -> new RuntimeException("未找到在住记录"));

        // 更新旧房间状态
        Room oldRoom = roomRepository.findById(oldRoomId)
                .orElseThrow(() -> new RuntimeException("旧房间不存在"));
        oldRoom.setStatus(2);
        oldRoom.setUpdateTime(LocalDateTime.now());
        roomRepository.save(oldRoom);

        // 更新新房间状态
        newRoom.setStatus(1);
        newRoom.setUpdateTime(LocalDateTime.now());
        roomRepository.save(newRoom);

        // 创建新的入住记录
        RoomRecord newRecord = new RoomRecord();
        newRecord.setHotelId(record.getHotelId());
        newRecord.setRoomId(newRoomId);
        newRecord.setRoomNo(newRoom.getRoomNo());
        newRecord.setGuestName(record.getGuestName());
        newRecord.setIdCard(record.getIdCard());
        newRecord.setPhone(record.getPhone());
        newRecord.setCheckInTime(LocalDateTime.now());
        newRecord.setStatus(1);
        newRecord.setRemark("换自" + oldRoom.getRoomNo() + "，原因：" + remark);
        newRecord.setCreateTime(LocalDateTime.now());
        newRecord.setUpdateTime(LocalDateTime.now());
        recordRepository.save(newRecord);

        // 更新旧记录
        record.setCheckOutTime(LocalDateTime.now());
        record.setStatus(0);
        record.setRemark("换至" + newRoom.getRoomNo() + "，原因：" + remark);
        record.setUpdateTime(LocalDateTime.now());
        recordRepository.save(record);
    }

    @Override
    @Transactional
    public void setRoomStatus(Integer roomId, Integer status, String remark) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("房间不存在"));
        room.setStatus(status);
        room.setRemark(remark);
        room.setUpdateTime(LocalDateTime.now());
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAvailableRooms(Integer hotelId) {
        return roomRepository.findByHotelIdAndStatus(hotelId, 0);
    }

    @Override
    public List<Room> getRoomsByStatus(Integer hotelId, Integer status) {
        return roomRepository.findByHotelIdAndStatus(hotelId, status);
    }

    @Override
    public RoomRecord getCurrentRecord(Integer roomId) {
        return recordRepository.findByRoomIdAndStatus(roomId, 1).orElse(null);
    }

    @Override
    public List<RoomRecord> getRoomRecords(Integer roomId) {
        return recordRepository.findByRoomIdOrderByCreateTimeDesc(roomId);
    }
}