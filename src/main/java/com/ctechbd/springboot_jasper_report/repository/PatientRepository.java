package com.ctechbd.springboot_jasper_report.repository;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByRegisterDateBetween(Date startDate, Date endDate);
}

