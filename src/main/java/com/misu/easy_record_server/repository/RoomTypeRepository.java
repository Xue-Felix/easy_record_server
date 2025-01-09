package com.misu.easy_record_server.repository;

import com.misu.easy_record_server.pojo.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    List<RoomType> findByHotelId(Integer hotelId);

    List<RoomType> findByHotelIdAndStatus(Integer hotelId, Integer status);
}