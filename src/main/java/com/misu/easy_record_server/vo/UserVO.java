package com.misu.easy_record_server.vo;

/**
 * @author x
 */
public class UserVO {
    private Integer id;
    private String username;
    private Integer hotelId;

    // 生成必要的Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }
}