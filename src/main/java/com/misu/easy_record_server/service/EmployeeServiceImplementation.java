package com.misu.easy_record_server.service;

import com.misu.easy_record_server.pojo.Employee;
import com.misu.easy_record_server.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(Integer departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<Employee> getEmployeesByPosition(Integer positionId) {
        return employeeRepository.findByPositionId(positionId);
    }

    @Override
    public List<Employee> getEmployeesByHotel(Integer hotelId) {
        return employeeRepository.findByHotelId(hotelId);
    }

    @Override
    public Employee getEmployeeByNo(String employeeNo) {
        return employeeRepository.findByEmployeeNo(employeeNo);
    }

    @Override
    public Page<Employee> searchEmployees(String keyword, Pageable pageable) {
        return employeeRepository.findAll((root, query, cb) -> cb.or(
                cb.like(root.get("name"), "%" + keyword + "%"),
                cb.like(root.get("employeeNo"), "%" + keyword + "%"),
                cb.like(root.get("phone"), "%" + keyword + "%"),
                cb.like(root.get("email"), "%" + keyword + "%")), pageable);
    }
}