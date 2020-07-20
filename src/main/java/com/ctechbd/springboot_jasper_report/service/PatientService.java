package com.ctechbd.springboot_jasper_report.service;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PatientService {
    Patient save(Patient patient);
    Patient update(Patient patient);
    Patient get(Long id);
    String delete(Long id);
    List<Patient> getAll();
}
