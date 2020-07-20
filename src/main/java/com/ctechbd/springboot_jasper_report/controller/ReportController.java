package com.ctechbd.springboot_jasper_report.controller;

import com.ctechbd.springboot_jasper_report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

@Controller
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.exportReport(format);
    }

    @GetMapping(value = "/report")
    public String generateReport(){
        return "custom_report";
    }

    @GetMapping(value = "/reportPdf")
    public HttpEntity<byte[]> getpdf(HttpServletResponse response) throws FileNotFoundException, JRException {
        return reportService.getPdfResponse(response);
    }
}
