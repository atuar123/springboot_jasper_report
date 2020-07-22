package com.ctechbd.springboot_jasper_report.controller;

import com.ctechbd.springboot_jasper_report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String generateReport() {
        return "custom_report";
    }

//    @GetMapping(value = "/reportPdf")
//    public HttpEntity<byte[]> getPdf(HttpServletResponse response) throws FileNotFoundException, JRException {
//        return reportService.getPdfResponse(response);
//    }

    @GetMapping(value = "/reportPdf")
    public HttpEntity<byte[]> getPdfWitDate(HttpServletResponse response, @RequestParam("startDate") String startDate,
                                            @RequestParam("endDate") String endDate) throws FileNotFoundException, JRException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startReportDate = null, endReportDate = null;
        try {
            startReportDate = sdf.parse(startDate);
            endReportDate = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reportService.getPdfResponseWithParams(response, startReportDate, endReportDate);
    }
}
