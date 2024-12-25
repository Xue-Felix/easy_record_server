package com.misu.easy_record_server.service;

import com.misu.easy_record_server.mapper.HotelMapper;
import com.misu.easy_record_server.pojo.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author x
 */
@Service
public class HotelServiceImplementation implements HotelService {

    final HotelMapper hotelMapper;

    HotelServiceImplementation(HotelMapper hotelMapper) {
        this.hotelMapper = hotelMapper;
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelMapper.save(hotel);
    }

    @Override
    public Hotel getHotelById(Integer id) {
        return hotelMapper.findById(id).orElse(null);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelMapper.findAll();
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        return hotelMapper.save(hotel);
    }

    @Override
    public void deleteHotel(Integer id) {
        hotelMapper.deleteById(id);
    }

    @Override
    public Page<Hotel> findAllHotelsPageable(Pageable pageable) {
        return hotelMapper.findAll(pageable);
    }

    @Override
    public List<Hotel> findHotelsByHotelNameLikeAndStarRatingGreaterThanEqual(String hotelName, int starRating) {
        return hotelMapper.findByHotelNameContainingAndStarRatingGreaterThanEqual(hotelName, starRating);
    }
}