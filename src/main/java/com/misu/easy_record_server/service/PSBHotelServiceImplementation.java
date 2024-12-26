package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Hotel;
import com.misu.easy_record_server.pojo.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

@Service
public class PSBHotelServiceImplementation implements PSBHotelService {
    private static final Logger logger = LoggerFactory.getLogger(PSBHotelServiceImplementation.class);

    @Value("${psb.hotel.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public PSBHotelServiceImplementation(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String checkOut(String psbId, String roomNo, String cardNum, String checkOutDate) {
        logger.info("Attempting to check out room: {} for psbId: {}", roomNo, psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/CheckOut")
                .queryParam("psbId", psbId)
                .queryParam("roomNo", roomNo)
                .queryParam("cardNum", cardNum)
                .queryParam("checkOutDate", checkOutDate)
                .toUriString();

        try {
            String result = restTemplate.getForObject(url, String.class);
            logger.info("Checkout result for room {}: {}", roomNo, result);
            return result;
        } catch (Exception e) {
            logger.error("Error during checkout for room {}: {}", roomNo, e.getMessage());
            throw new RuntimeException("退房操作失败", e);
        }
    }

    @Override
    public String changeRoom(String psbId, String sRoom, String dRoom, String cardNum) {
        logger.info("Attempting to change room from {} to {} for psbId: {}", sRoom, dRoom, psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/ChangeRoom")
                .queryParam("psbId", psbId)
                .queryParam("sRoom", sRoom)
                .queryParam("dRoom", dRoom)
                .queryParam("cardNum", cardNum)
                .toUriString();

        try {
            String result = restTemplate.getForObject(url, String.class);
            logger.info("Room change result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("Error during room change: {}", e.getMessage());
            throw new RuntimeException("换房操作失败", e);
        }
    }

    @Override
    public List<Room> getRoomList(String psbId) {
        logger.info("Fetching room list for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getRoomList")
                .queryParam("psbId", psbId)
                .toUriString();

        try {
            List<Room> rooms = restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() {
                    }).getBody();
            logger.info("Successfully retrieved {} rooms", rooms != null ? rooms.size() : 0);
            return rooms;
        } catch (Exception e) {
            logger.error("Error fetching room list: {}", e.getMessage());
            throw new RuntimeException("获取房间列表失败", e);
        }
    }

    @Override
    public String getGuestStatus(String psbId, String cardNum) {
        logger.info("Checking guest status for psbId: {} and cardNum: {}", psbId, cardNum);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getInfo")
                .queryParam("method", "1")
                .queryParam("psbId", psbId)
                .queryParam("cardNum", cardNum)
                .toUriString();

        try {
            String status = restTemplate.getForObject(url, String.class);
            logger.info("Guest status result: {}", status);
            return status;
        } catch (Exception e) {
            logger.error("Error checking guest status: {}", e.getMessage());
            throw new RuntimeException("获取客人状态失败", e);
        }
    }

    @Override
    public Hotel getHotelInfo(String psbId) {
        logger.info("Fetching hotel info for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getInfo")
                .queryParam("method", "2")
                .queryParam("psbId", psbId)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            if ("0".equals(response)) {
                logger.info("No hotel found for psbId: {}", psbId);
                return null;
            }
            Hotel hotel = restTemplate.getForObject(url, Hotel.class);
            logger.info("Successfully retrieved hotel info for psbId: {}", psbId);
            return hotel;
        } catch (Exception e) {
            logger.error("Error fetching hotel info: {}", e.getMessage());
            throw new RuntimeException("获取酒店信息失败", e);
        }
    }

    @Override
    public String getCheckOutInfo(String psbId) {
        logger.info("Fetching checkout info for psbId: {}", psbId);

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/getCheckOut")
                .queryParam("json", psbId)
                .toUriString();

        try {
            String result = restTemplate.getForObject(url, String.class);
            logger.info("Successfully retrieved checkout info");
            return result;
        } catch (Exception e) {
            logger.error("Error fetching checkout info: {}", e.getMessage());
            throw new RuntimeException("获取退房信息失败", e);
        }
    }
}