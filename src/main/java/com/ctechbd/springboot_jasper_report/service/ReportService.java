package com.ctechbd.springboot_jasper_report.service;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException;
    List<Patient> getAll();
}
