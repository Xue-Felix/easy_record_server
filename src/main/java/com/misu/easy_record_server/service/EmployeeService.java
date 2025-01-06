package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    Page<Employee> getAllEmployees(Pageable pageable);

    List<Employee> getEmployeesByDepartment(Integer departmentId);

    List<Employee> getEmployeesByPosition(Integer positionId);

    List<Employee> getEmployeesByHotel(Integer hotelId);

    Employee getEmployeeByNo(String employeeNo);

    Page<Employee> searchEmployees(String keyword, Pageable pageable);
}