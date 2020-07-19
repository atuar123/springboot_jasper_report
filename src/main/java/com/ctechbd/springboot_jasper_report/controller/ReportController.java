package com.ctechbd.springboot_jasper_report.controller;

import com.ctechbd.springboot_jasper_report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.exportReport(format);
    }
}
