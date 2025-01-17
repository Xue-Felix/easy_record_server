package com.misu.easy_record_server.task;

import com.misu.easy_record_server.pojo.Room;
import com.misu.easy_record_server.pojo.RoomRecord;
import com.misu.easy_record_server.repository.RoomRepository;
import com.misu.easy_record_server.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class RoomStatusCheckTask {

    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public RoomStatusCheckTask(RoomService roomService, RoomRepository roomRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @Scheduled(cron = "0 0 * * * *") // 每小时执行一次
    public void checkRoomStatus() {
        log.info("开始检查房间状态...");
        try {
            List<Room> occupiedRooms = roomRepository.findByStatus(1);
            LocalDateTime now = LocalDateTime.now();

            for (Room room : occupiedRooms) {
                RoomRecord record = roomService.getCurrentRecord(room.getId());
                if (record != null) {
                    // 检查是否超过预定退房时间
                    if (record.getCheckOutTime() != null && record.getCheckOutTime().isBefore(now)) {
                        log.warn("房间{}已超过预定退房时间", room.getRoomNo());
                        // 可以发送通知或采取其他措施
                    }
                }
            }
        } catch (Exception e) {
            log.error("房间状态检查失败", e);
        }
    }
}