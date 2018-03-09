package com.mkyong.dynamicreports;

import java.io.FileOutputStream;


import java.io.FileInputStream;

import java.io.FileNotFoundException;


import java.math.BigDecimal;

import java.util.Date;


import net.sf.dynamicreports.adhoc.AdhocManager;
import net.sf.dynamicreports.adhoc.configuration.AdhocColumn;
import net.sf.dynamicreports.adhoc.configuration.AdhocConfiguration;
import net.sf.dynamicreports.adhoc.configuration.AdhocReport;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

import net.sf.dynamicreports.report.datasource.DRDataSource;

import net.sf.dynamicreports.report.exception.DRException;

import net.sf.jasperreports.engine.JRDataSource;
import org.apache.commons.lang3.RandomStringUtils;


/*

@author Ricardo Mariaca(r.mariaca@dynamicreports.org)
        */

public class SimpleAdhocReport {


    public SimpleAdhocReport() {

        build();

    }


    public static JasperReportBuilder build() {

        AdhocConfiguration configuration = new AdhocConfiguration();

        AdhocReport report = new AdhocReport();

        configuration.setReport(report);


        AdhocColumn column = new AdhocColumn();

        column.setName("item");

        report.addColumn(column);


        column = new AdhocColumn();

        column.setName("quantity");

        report.addColumn(column);


        try {

            // The following code stores the configuration to an xml file

            AdhocManager.saveConfiguration(configuration, new FileOutputStream("d:/demo/configuration.xml"));

            @SuppressWarnings("unused")

            // The following code loads a configuration from an xml file

                    AdhocConfiguration loadedConfiguration = AdhocManager.loadConfiguration(new FileInputStream("d:/demo/configuration.xml"));


            JasperReportBuilder reportBuilder = AdhocManager.createReport(configuration.getReport());

            reportBuilder.setDataSource(createDataSource());
            //reportBuilder.show(false);
            //reportBuilder.print(true);
            return reportBuilder;


        } catch (DRException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }
        return null;

    }


    public static JRDataSource createDataSource() {

        DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");

        for (int i = 0; i < 20; i++) {

            dataSource.add("Book", new Date(), RandomStringUtils.randomNumeric(10) + 1, new BigDecimal(RandomStringUtils.randomNumeric(100) + 1));

        }

        return dataSource;

    }


    public static void main(String[] args) {

        new SimpleAdhocReport();

    }

}