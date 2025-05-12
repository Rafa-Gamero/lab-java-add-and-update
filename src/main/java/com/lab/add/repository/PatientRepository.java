package com.lab.add.repository;

import com.lab.add.models.Employee;
import com.lab.add.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDateOfBirthBetween(LocalDate start, LocalDate end);
    List<Patient> findByAdmittedByDepartment(String department);
    List<Patient> findByAdmittedByStatus(Employee.Status status);
}