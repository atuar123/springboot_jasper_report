package com.ctechbd.springboot_jasper_report.service;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {

    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
    List<Patient> getAll();

    HttpEntity<byte[]> getPdfResponse(HttpServletResponse response) throws FileNotFoundException, JRException;
}
