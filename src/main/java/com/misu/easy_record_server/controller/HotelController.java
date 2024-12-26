package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.common.ResponseStatus;
import com.misu.easy_record_server.pojo.Hotel;
import com.misu.easy_record_server.service.HotelService;
import com.misu.easy_record_server.vo.HotelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author x
 */
@RestController
@RequestMapping("/hotels")
public class HotelController {
    final HotelService hotelService;

    HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    private ResponseResult<List<HotelVO>> getListResponseResult(List<Hotel> hotels) {
        List<HotelVO> hotelViewOs = new ArrayList<>();

        for (Hotel hotel : hotels) {
            HotelVO hotelVO = new HotelVO();
            BeanUtils.copyProperties(hotel, hotelVO);
            hotelViewOs.add(hotelVO);
        }
        return ResponseResult.success(hotelViewOs);
    }

    // 创建酒店的接口
    @PostMapping
    public ResponseResult<HotelVO> createHotel(@Validated @RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(savedHotel, hotelVO);

        return ResponseResult.success(hotelVO);
    }

    // 根据酒店ID获取酒店信息的接口
    @GetMapping("/{id}")
    public ResponseResult<HotelVO> getHotelById(@PathVariable Integer id) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel == null) {
            return ResponseResult.fail(ResponseStatus.NOT_FOUND, "酒店不存在");
        }
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(hotel, hotelVO);
        return ResponseResult.success(hotelVO);
    }

    // 获取所有酒店信息的接口
    @GetMapping
    public ResponseResult<List<HotelVO>> getHotels() {
        var hotels = hotelService.getAllHotels();
        return getListResponseResult(hotels);
    }

    // 更新酒店信息的接口
    @PutMapping("/{id}")
    public ResponseResult<HotelVO> updateHotel(@PathVariable Integer id, @RequestBody Hotel hotel) {
        hotel.setHotelId(id);
        Hotel updatedHotel = hotelService.updateHotel(hotel);
        if (updatedHotel == null) {
            return ResponseResult.fail(ResponseStatus.NOT_FOUND, "酒店不存在");
        }
        HotelVO hotelVO = new HotelVO();
        BeanUtils.copyProperties(updatedHotel, hotelVO);
        return ResponseResult.success(hotelVO);
    }

    // 删除酒店的接口
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteHotel(@PathVariable Integer id) {
        hotelService.deleteHotel(id);
        return ResponseResult.success();
    }

    // 分页查询酒店信息的接口
    @GetMapping("/page")
    public ResponseResult<List<HotelVO>> getHotelsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hotel> hotelPage = hotelService.findAllHotelsPageable(pageable);
        List<HotelVO> hotelViewOs = new ArrayList<>();
        for (Hotel hotel : hotelPage.getContent()) {
            HotelVO hotelVO = new HotelVO();
            BeanUtils.copyProperties(hotel, hotelVO);
            hotelViewOs.add(hotelVO);
        }
        return ResponseResult.success(hotelViewOs);
    }

    // 根据酒店名称模糊查询且星级筛选酒店信息的接口
    @GetMapping("/search")
    public ResponseResult<List<HotelVO>> searchHotels(
            @RequestParam String hotelName,
            @RequestParam(defaultValue = "0") int starRating) {
        List<Hotel> hotels = hotelService.findHotelsByHotelNameLikeAndStarRatingGreaterThanEqual("%" + hotelName + "%",
                starRating);

        return getListResponseResult(hotels);
    }
}