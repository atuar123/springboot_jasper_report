package com.ctechbd.springboot_jasper_report.service.impl;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import com.ctechbd.springboot_jasper_report.repository.PatientRepository;
import com.ctechbd.springboot_jasper_report.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient getOne(Long id) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}
