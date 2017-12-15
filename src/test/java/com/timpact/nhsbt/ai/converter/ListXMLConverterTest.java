package com.timpact.nhsbt.ai.converter;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListXMLConverterTest {

    private ListXMLConverter CSVxmlConverter = new ListXMLConverterImpl();

        private String folderPath = "/Users/tezza/Documents/projects/hello-spring-cloud-master/src/test/resources/";

    @Test
    public void testConvertCSV2XML() throws Exception {
        String csvFilePath = folderPath + "csv/PhysiologicalData.csv";
        String xmlFilePath = folderPath + String.format("xml/PhysiologicalData_xml_%s.txt"
                , Math.random());
        CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
        try {
            List<String> headers = new ArrayList<String>();

            if (reader.readRecord()) {
                headers = Arrays.asList(reader.getValues());
            }
            if (headers.size() > 0) {
                while (reader.readRecord()) {
                    String xml = CSVxmlConverter.convertCSV2XML(Arrays.asList(reader.getValues()), headers);
                    List<String> lines = new ArrayList<String>();
                    lines.add(xml);
                    FileUtils.writeLines(
                            new File(xmlFilePath),
                            lines,
                            true);
                }
            }

        } finally {
            reader.close();
        }
    }

    @Test
    public void testConvertXML2CSV() throws Exception {
        String xmlFilePath = folderPath + "xml/PhysiologicalData.txt";
        String csvFilePath = folderPath + String.format("csv/PhysiologicalData_xml_%s.csv"
                , Math.random());
        Boolean orderKeyRequired = false;
        List<String> lines = FileUtils.readLines(new File(xmlFilePath), Charset.forName("UTF-8"));
        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
        try {
            if (lines.size() > 0) {
                List<String> headers = new ArrayList<String>();
                if (orderKeyRequired) {
                    SAXBuilder builder = new SAXBuilder();
                    Document doc = builder.build(new StringReader(lines.get(0)));
                    Element element = doc.getRootElement();
                    List<Element> elements = element.getChildren();;
                    for (Element element1:  elements) {
                        headers.add(element1.getName());
                    }
                } else {
//                    headers = Arrays.asList(new String[] {"Year","Organization","Department",
//                            "Position","Position count","Planned position count","Expense total",
//                                    "Course cost","Course days","Terminations","Internal hires","External hires"});

                    headers = Arrays.asList(new String[] {"cad","sod","dm","bgr","bp","sc","pcc","bu","sg",
                                    "pot","ane","appet","wbcc","class","su","pcv","htn","rbcc","al","hemo",
                                    "rbc","pc","pe","age","ba"});
                }
                csvWriter.writeRecord(headers.toArray(new String[headers.size()]));
                for (String line : lines) {
                    List<String> values = CSVxmlConverter.convertXML2CSV(line, headers);
                    csvWriter.writeRecord(values.toArray(new String[values.size()]));
                }
            }
        } finally {
            csvWriter.close();
        }
    }
}
