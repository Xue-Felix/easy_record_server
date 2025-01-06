package com.misu.easy_record_server.repository;

import com.misu.easy_record_server.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    List<Employee> findByDepartmentId(Integer departmentId);

    List<Employee> findByPositionId(Integer positionId);

    List<Employee> findByHotelId(Integer hotelId);

    Employee findByEmployeeNo(String employeeNo);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}