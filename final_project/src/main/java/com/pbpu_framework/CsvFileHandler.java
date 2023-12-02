package com.pbpu_framework;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler implements DataFileHandler<List<Data>> {

    private final String dataPath = "data/";

    @Override
    public List<Data> readFile(String fileName) throws IOException {
        List<Data> dataList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(dataPath + fileName))) {
            String[] nextLine;
            try {
                while ((nextLine = reader.readNext()) != null) {
                    if (nextLine.length >= 2) {
                        String subject = nextLine[0].trim();
                        String description = nextLine[1].trim();
                        dataList.add(new Data(subject, description));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    @Override
    public void writeFile(String fileName, List<Data> dataList) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(dataPath + fileName))) {
            for (Data data : dataList) {
                String[] line = { data.getSubject(), data.getDescription() };
                writer.writeNext(line);
            }
        }
    }
}
