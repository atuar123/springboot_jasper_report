package com.ctechbd.springboot_jasper_report.service.impl;

import com.ctechbd.springboot_jasper_report.entities.Patient;
import com.ctechbd.springboot_jasper_report.repository.PatientRepository;
import com.ctechbd.springboot_jasper_report.service.ReportService;
import net.sf.jasperreports.chrome.BrowserService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final PatientRepository patientRepository;

    public ReportServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\cTech\\Report";
        List<Patient> patients = getAll();

        File file = ResourceUtils.getFile("classpath:Patients.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patients);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\patients.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\patients.pdf");
        }
        return "Reported Generated in path: " + path;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> findByDate(Date registerStart, Date registerEnd) {
        return patientRepository.findByRegisterDateBetween(registerStart, registerEnd);
    }

    @Override
    public HttpEntity<byte[]> getPdfResponse(HttpServletResponse response) throws FileNotFoundException, JRException {
        File fileName = ResourceUtils.getFile("classpath:customer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileName.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getAll());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        response.setHeader("Content-Description", "attachment; filename=abc.pdf");
        return new HttpEntity<>(pdfBytes, headers);
    }

    @Override
    public HttpEntity<byte[]> getPdfResponseWithParams(HttpServletResponse response, Date startDate, Date endDate) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:customer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findByDate(startDate, endDate));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource);
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        response.setHeader("Content-Description", "attachment; filename=abc.pdf");
        return new HttpEntity<>(pdfBytes, headers);
    }

    @Override
    public void generateReport(HttpServletResponse response, String format) throws IOException, JRException {
        File fileName = ResourceUtils.getFile("classpath:customer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(fileName.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getAll());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Admin");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (format.equalsIgnoreCase("pdf")) {
            /**
             * 1- export to PDF
             */
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=report.pdf");
            response.getOutputStream().write(pdfBytes);
            response.flushBuffer();
        }
        /**
         * 3- export to Excel sheet
         */
        else if (format.equalsIgnoreCase("xlsx")) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
            exporter.exportReport();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=report.xlsx");
            response.getOutputStream().write(os.toByteArray());
            response.flushBuffer();
        }
        /**
         * 2- export to CSV
         */
        else if (format.equalsIgnoreCase("csv")) {
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            StringBuffer buffer = new StringBuffer();
            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, buffer);
            exporter.exportReport();

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=report.csv");
            response.getOutputStream().write(buffer.toString().getBytes());
            response.flushBuffer();
        }
    }
}
