package com.ctechbd.springboot_jasper_report.controller;

import com.ctechbd.springboot_jasper_report.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping(value = "/Report")
    public HttpEntity<byte[]> getPdfWithDate(HttpServletResponse response, @RequestParam("startDate") String startDate,
                                            @RequestParam("endDate") String endDate) throws FileNotFoundException, JRException {

        String stDate= startDate + " 00:00:00";
        String edDate= endDate + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startReportDate = null, endReportDate = null;
        try {
            startReportDate = sdf.parse(stDate);
            endReportDate = sdf.parse(edDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reportService.getPdfResponseWithParams(response, startReportDate, endReportDate);
    }

    @RequestMapping(value = "getPdf", method = RequestMethod.GET)
    @ResponseBody
    public void getPdf(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/customer.jrxml");
        JasperDesign design = JRXmlLoader.load(jasperStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportService.getAll());
        Map<String,Object> params = new HashMap<>();
        params.put("createdBy", "Admin");
        //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "inline; filename=Report.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

}
