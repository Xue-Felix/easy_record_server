package com.misu.easy_record_server.pojo;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_room_record")
public class RoomRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer hotelId; // 酒店ID

    @Column(nullable = false)
    private Integer roomId; // 房间ID

    @Column(nullable = false, length = 20)
    private String roomNo; // 房间号

    @Column(nullable = false, length = 50)
    private String guestName; // 客人姓名

    @Column(nullable = false, length = 20)
    private String idCard; // 身份证号

    @Column(nullable = false, length = 20)
    private String phone; // 联系电话

    private LocalDateTime checkInTime; // 入住时间

    private LocalDateTime checkOutTime; // 退房时间

    private Integer status; // 状态 0:已退房 1:在住

    private String remark; // 备注

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}