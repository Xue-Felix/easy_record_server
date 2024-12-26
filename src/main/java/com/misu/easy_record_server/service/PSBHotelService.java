package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Hotel;
import com.misu.easy_record_server.pojo.Room;

import java.util.List;

public interface PSBHotelService {
    /**
     * 退房
     *
     * @param psbId        酒店代码
     * @param roomNo       房间号
     * @param cardNum      证件号
     * @param checkOutDate 离店时间
     * @return 退房结果，1表示成功，其他值表示失败原因
     */
    String checkOut(String psbId, String roomNo, String cardNum, String checkOutDate);

    /**
     * 换房
     *
     * @param psbId   旅馆代码
     * @param sRoom   原房号
     * @param dRoom   新房号
     * @param cardNum 证件号
     * @return 换房结果，1表示成功，其他值表示失败原因
     */
    String changeRoom(String psbId, String sRoom, String dRoom, String cardNum);

    /**
     * 获取房间列表
     *
     * @param psbId 旅馆代码
     * @return Room对象列表
     */
    List<Room> getRoomList(String psbId);

    /**
     * 获取客人是否在住信息
     *
     * @param psbId   旅馆代码
     * @param cardNum 证件号
     * @return 0表示不在住，1表示在住
     */
    String getGuestStatus(String psbId, String cardNum);

    /**
     * 获取酒店信息
     *
     * @param psbId 旅馆代码
     * @return Hotel对象，如果未匹配到返回null
     */
    Hotel getHotelInfo(String psbId);

    /**
     * 退房查询
     *
     * @param psbId 旅馆代码
     * @return 退房记录信息
     */
    String getCheckOutInfo(String psbId);
}
