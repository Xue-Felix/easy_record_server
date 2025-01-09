package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.RoomType;
import com.misu.easy_record_server.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomTypeServiceImplementation implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImplementation(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    @Transactional
    public RoomType createRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    @Override
    @Transactional
    public RoomType updateRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    @Override
    @Transactional
    public void deleteRoomType(Integer id) {
        roomTypeRepository.deleteById(id);
    }

    @Override
    public RoomType getRoomTypeById(Integer id) {
        return roomTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<RoomType> getAllRoomTypesByHotel(Integer hotelId) {
        return roomTypeRepository.findByHotelId(hotelId);
    }

    @Override
    public List<RoomType> getActiveRoomTypesByHotel(Integer hotelId) {
        return roomTypeRepository.findByHotelIdAndStatus(hotelId, 1);
    }
}