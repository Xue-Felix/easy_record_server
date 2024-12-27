package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.common.ResponseStatus;
import com.misu.easy_record_server.pojo.Hotel;
import com.misu.easy_record_server.pojo.PSBHotel;
import com.misu.easy_record_server.pojo.PSBRoom;
import com.misu.easy_record_server.service.HotelService;
import com.misu.easy_record_server.service.PSBHotelService;
import com.misu.easy_record_server.vo.HotelVO;

import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/hotel")
@Slf4j
public class HotelController {
    final HotelService hotelService;
    final PSBHotelService psbHotelService;

    HotelController(HotelService hotelService, PSBHotelService psbHotelService) {
        this.hotelService = hotelService;
        this.psbHotelService = psbHotelService;
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

    @GetMapping("/rooms/{psbId}")
    public ResponseResult<List<PSBRoom>> getRoomList(@PathVariable String psbId) {
        log.info("Getting room list for psbId: {}", psbId);
        List<PSBRoom> rooms = psbHotelService.getRoomList(psbId);
        return ResponseResult.success(rooms);
    }

    @PostMapping("/checkout")
    public ResponseResult<String> checkOut(@RequestParam String psbId,
            @RequestParam String roomNo,
            @RequestParam String cardNum,
            @RequestParam String checkOutDate) {
        log.info("Processing checkout for room: {}, cardNum: {}", roomNo, cardNum);
        String result = psbHotelService.checkOut(psbId, roomNo, cardNum, checkOutDate);
        return ResponseResult.success(result);
    }

    @GetMapping("/checkout/info/{psbId}")
    public ResponseResult<?> getCheckOutInfo(@PathVariable String psbId) {
        log.info("Getting checkout info for psbId: {}", psbId);
        String result = psbHotelService.getCheckOutInfo(psbId);
        return ResponseResult.success(result);
    }

    @GetMapping("/guest-status")
    public ResponseResult<String> getGuestStatus(@RequestParam String psbId,
            @RequestParam String cardNum) {
        log.info("Checking guest status for cardNum: {}", cardNum);
        String status = psbHotelService.getGuestStatus(psbId, cardNum);
        return ResponseResult.success(status);
    }

    @PostMapping("/change-room")
    public ResponseResult<String> changeRoom(@RequestParam String psbId,
            @RequestParam String sRoom,
            @RequestParam String dRoom,
            @RequestParam String cardNum) {
        log.info("Processing room change from {} to {} for cardNum: {}", sRoom, dRoom, cardNum);
        String result = psbHotelService.changeRoom(psbId, sRoom, dRoom, cardNum);
        return ResponseResult.success(result);
    }

    @GetMapping("/info/{psbId}")
    public ResponseResult<?> getHotelInfo(@PathVariable String psbId) {
        log.info("Getting hotel info for psbId: {}", psbId);
        PSBHotel hotel = psbHotelService.getHotelInfo(psbId);

        return ResponseResult.success(hotel);
    }

}