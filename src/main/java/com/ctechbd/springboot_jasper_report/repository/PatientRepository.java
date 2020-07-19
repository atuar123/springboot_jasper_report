package com.ctechbd.springboot_jasper_report.repository;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
