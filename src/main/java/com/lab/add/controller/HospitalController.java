package com.lab.add.controller;

import com.lab.add.models.Employee;
import com.lab.add.models.Patient;
import com.lab.add.repository.EmployeeRepository;
import com.lab.add.repository.PatientRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HospitalController {

    private final PatientRepository patientRepository;
    private final EmployeeRepository employeeRepository;

    public HospitalController(PatientRepository patientRepository, EmployeeRepository employeeRepository) {
        this.patientRepository = patientRepository;
        this.employeeRepository = employeeRepository;
    }

    // 1. Add new patient
    @PostMapping("/patients")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    // 2. Add new doctor (using Employee)
    @PostMapping("/doctors")
    public ResponseEntity<Employee> addDoctor(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // 3. Change doctor status
    @PatchMapping("/doctors/{id}/status")
    public ResponseEntity<Employee> changeDoctorStatus(
            @PathVariable Long id,
            @RequestParam Employee.Status status) {

        Optional<Employee> doctorOptional = employeeRepository.findById(id);
        if (doctorOptional.isPresent()) {
            Employee doctor = doctorOptional.get();
            doctor.setStatus(status);
            employeeRepository.save(doctor);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 4. Update doctor's department
    @PatchMapping("/doctors/{id}/department")
    public ResponseEntity<Employee> updateDoctorDepartment(
            @PathVariable Long id,
            @RequestParam String department) {

        Optional<Employee> doctorOptional = employeeRepository.findById(id);
        if (doctorOptional.isPresent()) {
            Employee doctor = doctorOptional.get();
            doctor.setDepartment(department);
            employeeRepository.save(doctor);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Update patient information
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody Patient patientDetails) {

        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setName(patientDetails.getName());
            patient.setDateOfBirth(patientDetails.getDateOfBirth());
            patient.setAdmittedBy(patientDetails.getAdmittedBy()); //  Consider carefully how you want to handle this.  Do you want to allow changing the doctor?
            Patient updatedPatient = patientRepository.save(patient);
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}