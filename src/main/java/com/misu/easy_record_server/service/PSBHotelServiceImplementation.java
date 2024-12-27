package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.PSBHotel;
import com.misu.easy_record_server.pojo.PSBRoom;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class PSBHotelServiceImplementation implements PSBHotelService {
    @Value("${psb.hotel.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public PSBHotelServiceImplementation(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String checkOut(String psbId, String roomNo, String cardNum, String checkOutDate) {
        log.info("Attempting to check out room: {} for psbId: {}", roomNo, psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/CheckOut")
                .queryParam("psbId", psbId)
                .queryParam("roomNo", roomNo)
                .queryParam("cardNum", cardNum)
                .queryParam("checkOutDate", checkOutDate)
                .toUriString();

        try {
            String result = restTemplate.getForObject(url, String.class);
            log.info("Checkout result for room {}: {}", roomNo, result);
            return result;
        } catch (Exception e) {
            log.error("Error during checkout for room {}: {}", roomNo, e.getMessage());
            throw new RuntimeException("退房操作失败", e);
        }
    }

    @Override
    public String changeRoom(String psbId, String sRoom, String dRoom, String cardNum) {
        log.info("Attempting to change room from {} to {} for psbId: {}", sRoom, dRoom, psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/ChangeRoom")
                .queryParam("psbId", psbId)
                .queryParam("sRoom", sRoom)
                .queryParam("dRoom", dRoom)
                .queryParam("cardNum", cardNum)
                .toUriString();

        try {
            String result = restTemplate.getForObject(url, String.class);
            log.info("Room change result: {}", result);
            return result;
        } catch (Exception e) {
            log.error("Error during room change: {}", e.getMessage());
            throw new RuntimeException("换房操作失败", e);
        }
    }

    @Override
    public List<PSBRoom> getRoomList(String psbId) {
        log.info("Fetching room list for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getRoomList")
                .queryParam("psbId", psbId)
                .toUriString();

        log.info("request url: {}", url);

        try {
            HashMap<String, Object> paramMap = new HashMap<>();

            String result = HttpUtil.post(url, paramMap);

            // String result = HttpRequest.post(url)
            // .form(paramMap)// 表单内容
            // .timeout(20000)// 超时，毫秒
            // .execute().body();

            JSONArray array = JSONUtil.parseArray(result);

            List<PSBRoom> rooms = JSONUtil.toList(array, PSBRoom.class);

            log.info("Successfully retrieved {} rooms", rooms != null ? rooms.size() : 0);

            return rooms;
        } catch (Exception e) {
            log.error("Error fetching room list: {}", e.getMessage());
            throw new RuntimeException("获取房间列表失败", e);
        }
    }

    @Override
    public String getGuestStatus(String psbId, String cardNum) {
        log.info("Checking guest status for psbId: {} and cardNum: {}", psbId, cardNum);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getInfo")
                .queryParam("method", "1")
                .queryParam("psbId", psbId)
                .queryParam("cardNum", cardNum)
                .toUriString();

        try {
            String status = restTemplate.getForObject(url, String.class);
            log.info("Guest status result: {}", status);
            return status;
        } catch (Exception e) {
            log.error("Error checking guest status: {}", e.getMessage());
            throw new RuntimeException("获取客人状态失败", e);
        }
    }

    @Override
    public PSBHotel getHotelInfo(String psbId) {
        log.info("Fetching hotel info for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getInfo")
                .queryParam("method", "2")
                .queryParam("psbId", psbId)
                .toUriString();

        try {
            HashMap<String, Object> paramMap = new HashMap<>();

            String result = HttpUtil.post(url, paramMap);

            PSBHotel hotel = JSONUtil.toBean(result, PSBHotel.class);

            log.info("Successfully retrieved hotel info for psbId: {}", psbId);
            return hotel;
        } catch (Exception e) {
            log.error("Error fetching hotel info: {}", e.getMessage());
            throw new RuntimeException("获取酒店信息失败", e);
        }
    }

    @Override
    public String getCheckOutInfo(String psbId) {
        log.info("Fetching checkout info for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getInfo")
                .queryParam("json", psbId)
                .toUriString();

        try {
            HashMap<String, Object> paramMap = new HashMap<>();

            String result = HttpUtil.post(url, paramMap);

            log.info("Successfully retrieved checkout info");
            return result;
        } catch (Exception e) {
            log.error("Error fetching checkout info: {}", e.getMessage());
            throw new RuntimeException("获取退房信息失败", e);
        }
    }
}