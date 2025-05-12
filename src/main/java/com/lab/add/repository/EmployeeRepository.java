package com.lab.add.repository;

import com.lab.add.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByStatus(Employee.Status status);
    List<Employee> findByDepartment(String department);
}
