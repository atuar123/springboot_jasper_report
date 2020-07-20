package com.ctechbd.springboot_jasper_report.service.impl;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import com.ctechbd.springboot_jasper_report.repository.PatientRepository;
import com.ctechbd.springboot_jasper_report.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Patient get(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent() && patientOptional.get() != null){
            Patient patient = patientOptional.get();
            return patient;
        }
        return null;
    }

    @Override
    public String delete(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent() && patientOptional.get() != null){
            Patient patient = patientOptional.get();
            patientRepository.delete(patient);
            return "deletedSuccessfully";
        }
        return "recordNotFound";
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}
