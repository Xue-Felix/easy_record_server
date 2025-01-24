package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author x
 */
public interface HotelService {
    Hotel saveHotel(Hotel hotel);

    Hotel getHotelById(Integer id);

    List<Hotel> getAllHotels();

    Hotel updateHotel(Hotel hotel);

    void deleteHotel(Integer id);

    // List<Hotel> getHotelsByUserId(Integer userId);

    // 分页查询酒店
    Page<Hotel> findAllHotelsPageable(Pageable pageable);

    // 根据条件查询酒店（示例以酒店名称模糊查询以及星级筛选为例，可按需扩展更多条件）
    List<Hotel> findHotelsByHotelNameLikeAndStarRatingGreaterThanEqual(String hotelName, int starRating);
}