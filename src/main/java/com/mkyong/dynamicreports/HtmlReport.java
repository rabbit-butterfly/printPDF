/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 * <p>
 * Copyright (C) 2010 - 2018 Ricardo Mariaca
 * http://www.dynamicreports.org
 * <p>
 * This file is part of DynamicReports.
 * <p>
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mkyong.dynamicreports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;

import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class HtmlReport {

    public HtmlReport() {
        build();
    }

    private void build() {
        try {
            JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("c:/report.html")
                    .setImagesDirName("c:/images")
                    .setOutputImagesToDir(true);

            report()
                    .setTemplate(Templates.reportTemplate)
                    .columns(
                            col.column("Item", "item", type.stringType()),
                            col.column("Quantity", "quantity", type.integerType()),
                            col.column("Unit price", "unitprice", type.bigDecimalType()))
                    .title(Templates.createTitleComponent("HtmlReport"))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(createDataSource())
                    .toHtml(htmlExporter);
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
        for (int i = 0; i < 20; i++) {
            dataSource.add("Book", (int) (Math.random() * 10) + 1, new BigDecimal(Math.random() * 100 + 1));
        }
        return dataSource;
    }

    public static void main(String[] args) {
        new HtmlReport();
    }
}