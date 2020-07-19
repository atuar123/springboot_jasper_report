package com.ctechbd.springboot_jasper_report.controller;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import com.ctechbd.springboot_jasper_report.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home( Model model){
        model.addAttribute("patients", patientService.getAll());
        return "index";
    }
    @GetMapping(value = "/patient")
    public String patient(Patient patient){
        return "add_patient";
    }

    @PostMapping(value = "/savePatient")
    public String addPatient(@Valid Patient patient, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add_patient";
        }
        patientService.save(patient);
        model.addAttribute("patients", patientService.getAll());
        return "index";
    }
}
