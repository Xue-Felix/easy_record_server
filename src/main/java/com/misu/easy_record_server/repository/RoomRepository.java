package com.misu.easy_record_server.repository;

import com.misu.easy_record_server.pojo.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    // 根据酒店ID查询所有房间
    List<Room> findByHotelId(Integer hotelId);

    // 分页查询酒店房间
    Page<Room> findByHotelId(Integer hotelId, Pageable pageable);

    // 根据酒店ID和状态查询房间
    List<Room> findByHotelIdAndStatus(Integer hotelId, Integer status);

    // 根据状态查询房间
    List<Room> findByStatus(Integer status);

    // 根据楼层查询房间
    List<Room> findByHotelIdAndFloor(Integer hotelId, String floor);

    // 根据房间号查询房间
    Room findByHotelIdAndRoomNo(Integer hotelId, String roomNo);

    // 根据房型查询房间
    List<Room> findByHotelIdAndRoomTypeId(Integer hotelId, Integer roomTypeId);

    // 查询指定酒店的空闲房间数量
    @Query("SELECT COUNT(r) FROM Room r WHERE r.hotelId = :hotelId AND r.status = 0")
    Long countAvailableRooms(@Param("hotelId") Integer hotelId);

    // 查询指定酒店各状态的房间数量
    @Query("SELECT r.status, COUNT(r) FROM Room r WHERE r.hotelId = :hotelId GROUP BY r.status")
    List<Object[]> countRoomsByStatus(@Param("hotelId") Integer hotelId);

    // 查询指定酒店各房型的房间数量
    @Query("SELECT r.roomTypeId, COUNT(r) FROM Room r WHERE r.hotelId = :hotelId GROUP BY r.roomTypeId")
    List<Object[]> countRoomsByType(@Param("hotelId") Integer hotelId);

    // 查询指定酒店各楼层的房间数量
    @Query("SELECT r.floor, COUNT(r) FROM Room r WHERE r.hotelId = :hotelId GROUP BY r.floor")
    List<Object[]> countRoomsByFloor(@Param("hotelId") Integer hotelId);

    // 搜索房间（模糊查询）
    @Query("SELECT r FROM Room r WHERE r.hotelId = :hotelId AND " +
            "(r.roomNo LIKE %:keyword% OR r.floor LIKE %:keyword% OR r.remark LIKE %:keyword%)")
    Page<Room> searchRooms(@Param("hotelId") Integer hotelId,
            @Param("keyword") String keyword,
            Pageable pageable);

    // 检查房间号是否已存在
    boolean existsByHotelIdAndRoomNo(Integer hotelId, String roomNo);
}