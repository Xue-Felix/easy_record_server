package com.misu.easy_record_server.controller;

import com.misu.easy_record_server.common.ResponseResult;
import com.misu.easy_record_server.pojo.Employee;
import com.misu.easy_record_server.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseResult<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseResult.success(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseResult<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return ResponseResult.success(employeeService.updateEmployee(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseResult.success();
    }

    @GetMapping("/{id}")
    public ResponseResult<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseResult.success(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseResult<Page<Employee>> getAllEmployees(Pageable pageable) {
        return ResponseResult.success(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/search")
    public ResponseResult<Page<Employee>> searchEmployees(
            @RequestParam String keyword,
            Pageable pageable) {
        return ResponseResult.success(employeeService.searchEmployees(keyword, pageable));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseResult<List<Employee>> getEmployeesByHotel(@PathVariable Integer hotelId) {
        return ResponseResult.success(employeeService.getEmployeesByHotel(hotelId));
    }
}