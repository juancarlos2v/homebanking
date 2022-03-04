package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.HomebankingApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PDFExportController {

private final HomebankingApplication.PDFGeneratorService pdfGeneratorService;

    public PDFExportController(HomebankingApplication.PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/pdf/generate")
    public  void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
        String currentDateTime= dateFormat.format(new Date());

        String headerKey="Content-Disposition";
        String valueKey="attachment; filename=pdf "+currentDateTime+" .pdf";

        response.setHeader(headerKey,valueKey);

        this.pdfGeneratorService.export(response);

    }
}
