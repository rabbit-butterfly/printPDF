package com.mkyong;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.mkyong.dynamicreports.SimpleAdhocReport;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WelcomeController {


    private static final Logger log =
            LoggerFactory.getLogger(WelcomeController.class);

    private static final String PARAMETER_ITEM_NAME = "itemName";
    private static final String PARAMETER_TYPE = "type";
    private static final String VALUE_TYPE_PDF = "pdf";
    private static final String VALUE_TYPE_XLS = "xls";

    private static final Map<String, String> FILE_TYPE_2_CONTENT_TYPE =
            new HashMap<String, String>();

    static {
        FILE_TYPE_2_CONTENT_TYPE.put(VALUE_TYPE_PDF, "application/pdf");
        FILE_TYPE_2_CONTENT_TYPE.put(VALUE_TYPE_XLS, "application/vnd.ms-excel");
    }

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/print")
    public String print(Map<String, Object> model) {

        new SimpleAdhocReport();


        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fileType = request.getParameter(PARAMETER_TYPE);
        log.info("Exporting {} report", fileType);

        response.setContentType(FILE_TYPE_2_CONTENT_TYPE.get(fileType));
        OutputStream out = response.getOutputStream();
        try {
            JasperReportBuilder jrb = SimpleAdhocReport.build();

            if (VALUE_TYPE_PDF.equals(fileType)) {
                jrb.toPdf(out);
            } else if (VALUE_TYPE_XLS.equals(fileType)) {
                jrb.toXls(out);
                //jrb.toExcelApiXls(out);
            }
        } catch (DRException e) {
            throw new ServletException(e);
        }
        out.close();
    }
}