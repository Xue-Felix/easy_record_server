package com.misu.easy_record_server.pojo;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author x
 *         Hotel 实体
 */
@Entity
@Table(name = "tb_hotel")
public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;

    @Column(nullable = false, length = 100)
    private String hotelName;

    private String address;

    private String logo;

    private Integer starRating = 3;

    private String contactPhone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createTime = LocalDateTime.now();

    private LocalDateTime updateTime = LocalDateTime.now();

    private String managerName;

    private String managerContact;

    private String banner; // 酒店Banner图片URL，多个图片用逗号分隔

    @Column(length = 200)
    private String slogan; // 酒店口号愿景

    // 生成必要的Getter和Setter方法
    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerContact() {
        return managerContact;
    }

    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

    @Transient // 这个字段不会映射到数据库
    public List<String> getBannerList() {
        if (banner == null || banner.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(banner.split(","));
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
