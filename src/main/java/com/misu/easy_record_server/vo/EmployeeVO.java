package com.misu.easy_record_server.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeVO {
    private Long id;
    private String name;
    private String employeeNo;
    private String departmentName;
    private String positionName;
    private LocalDateTime createTime;
}