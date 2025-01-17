package com.misu.easy_record_server.mapper;

import com.misu.easy_record_server.pojo.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author x
 */
@Repository
public interface HotelMapper extends JpaRepository<Hotel, Integer> {
    // 同样可以自定义一些特定的查询方法，比如按酒店名称模糊查询等
    // 示例：List<Hotel> findByHotelNameContaining(String hotelName);

    // 根据用户ID查询酒店
    List<Hotel> findByUserId(Integer userId);

    // 自定义根据酒店名称模糊查询且星级大于等于指定值的方法
    List<Hotel> findByHotelNameContainingAndStarRatingGreaterThanEqual(String hotelName, int starRating);
}