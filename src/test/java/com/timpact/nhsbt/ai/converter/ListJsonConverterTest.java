package com.timpact.nhsbt.ai.converter;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ListJsonConverterTest {

    private ListJsonConverter CSVJsonConverter = new ListJsonConverterImpl();

    private String folderPath = "/Users/tezza/Documents/projects/hello-spring-cloud-master/src/test/resources/";

    @Test
    public void testConvertCSV2JSON() throws Exception {
        String csvFilePath = folderPath + "csv/PhysiologicalData.csv";
        String jsonFilePath = folderPath + String.format("json/PhysiologicalData_json_%s.txt"
                , Math.random());
        CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
        try {
            List<String> headers = new ArrayList<String>();

            if (reader.readRecord()) {
                headers = Arrays.asList(reader.getValues());
            }
            if (headers.size() > 0) {
                while (reader.readRecord()) {
                    String json = CSVJsonConverter.convertCSV2JSON(Arrays.asList(reader.getValues()), headers);
                    List<String> lines = new ArrayList<String>();
                    lines.add(json);
                    FileUtils.writeLines(
                            new File(jsonFilePath),
                            lines,
                            true);
                }
            }

        } finally {
            reader.close();
        }
    }

    @Test
    public void testConvertJSON2CSV() throws Exception {
        String jsonFilePath = folderPath + "json/PhysiologicalData.txt";
        String csvFilePath = folderPath + String.format("csv/PhysiologicalData_json_%s.csv"
                , Math.random());
        Boolean orderKeyRequired = false;
        List<String> lines = FileUtils.readLines(new File(jsonFilePath), Charset.forName("UTF-8"));
        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));
        try {
            if (lines.size() > 0) {
                List<String> headers = new ArrayList<String>();
                if (orderKeyRequired) {
                    JSONObject json = new JSONObject(lines.get(0));
                    Iterator<String> keys = (Iterator<String>) json.keys();
                    while (keys.hasNext()) {
                        headers.add(keys.next().toString());
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
                    List<String> values = CSVJsonConverter.convertJSON2CSV(line, headers);
                    csvWriter.writeRecord(values.toArray(new String[values.size()]));
                }
            }
        } finally {
            csvWriter.close();
        }
    }
}
