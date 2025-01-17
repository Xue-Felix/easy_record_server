package com.misu.easy_record_server.pojo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer hotelId; // 所属酒店ID

    @Column(nullable = false, length = 20)
    private String roomNo; // 房间号

    @Column(nullable = false)
    private Integer roomTypeId; // 房间类型ID

    @Column(nullable = false)
    private Integer status; // 状态 0:空闲 1:已住 2:打扫 3:维修

    private String floor; // 楼层

    private String remark; // 备注

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}