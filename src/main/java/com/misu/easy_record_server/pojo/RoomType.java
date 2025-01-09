package com.misu.easy_record_server.pojo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_room_type")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String typeName; // 房型名称

    @Column(nullable = false)
    private Integer hotelId; // 所属酒店ID

    private String description; // 房型描述

    private Integer maxGuests; // 最大容纳人数

    private Double basePrice; // 基础价格

    private Double hourPrice; // 钟点房价格

    private Integer status; // 状态 1:启用 0:停用

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate() {
        createTime = updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}