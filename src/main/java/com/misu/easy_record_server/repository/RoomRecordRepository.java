package com.misu.easy_record_server.repository;

import com.misu.easy_record_server.pojo.RoomRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRecordRepository extends JpaRepository<RoomRecord, Integer> {

    // 查询房间当前入住记录
    Optional<RoomRecord> findByRoomIdAndStatus(Integer roomId, Integer status);

    // 查询房间的所有入住记录（按时间倒序）
    List<RoomRecord> findByRoomIdOrderByCreateTimeDesc(Integer roomId);

    // 分页查询酒店的入住记录
    Page<RoomRecord> findByHotelIdOrderByCreateTimeDesc(Integer hotelId, Pageable pageable);

    // 查询指定时间范围内的入住记录
    @Query("SELECT r FROM RoomRecord r WHERE r.hotelId = :hotelId AND " +
            "r.checkInTime BETWEEN :startTime AND :endTime")
    List<RoomRecord> findByCheckInTimeBetween(
            @Param("hotelId") Integer hotelId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    // 查询指定客人的所有入住记录
    List<RoomRecord> findByHotelIdAndGuestNameOrderByCreateTimeDesc(
            Integer hotelId, String guestName);

    // 查询指定身份证号的所有入住记录
    List<RoomRecord> findByHotelIdAndIdCardOrderByCreateTimeDesc(
            Integer hotelId, String idCard);

    // 查询指定手机号的所有入住记录
    List<RoomRecord> findByHotelIdAndPhoneOrderByCreateTimeDesc(
            Integer hotelId, String phone);

    // 统计当日入住人数
    @Query("SELECT COUNT(r) FROM RoomRecord r WHERE r.hotelId = :hotelId AND " +
            "DATE(r.checkInTime) = CURRENT_DATE")
    Long countTodayCheckIns(@Param("hotelId") Integer hotelId);

    // 统计当日退房人数
    @Query("SELECT COUNT(r) FROM RoomRecord r WHERE r.hotelId = :hotelId AND " +
            "DATE(r.checkOutTime) = CURRENT_DATE")
    Long countTodayCheckOuts(@Param("hotelId") Integer hotelId);

    // 搜索入住记录（模糊查询）
    @Query("SELECT r FROM RoomRecord r WHERE r.hotelId = :hotelId AND " +
            "(r.guestName LIKE %:keyword% OR r.roomNo LIKE %:keyword% OR " +
            "r.phone LIKE %:keyword% OR r.idCard LIKE %:keyword%)")
    Page<RoomRecord> searchRecords(
            @Param("hotelId") Integer hotelId,
            @Param("keyword") String keyword,
            Pageable pageable);

    // 统计指定时间范围内的入住率
    @Query("SELECT COUNT(DISTINCT r.roomId) * 100.0 / " +
            "(SELECT COUNT(rm) FROM Room rm WHERE rm.hotelId = :hotelId) " +
            "FROM RoomRecord r WHERE r.hotelId = :hotelId AND " +
            "r.checkInTime BETWEEN :startTime AND :endTime")
    Double calculateOccupancyRate(
            @Param("hotelId") Integer hotelId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}