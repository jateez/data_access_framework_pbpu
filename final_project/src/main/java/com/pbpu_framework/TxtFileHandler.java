package com.pbpu_framework;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileHandler implements DataFileHandler<List<Data>> {

    private final String dataPath = "data/";

    @Override
    public List<Data> readFile(String fileName) throws IOException {
        Path filePath = Paths.get(dataPath, fileName);

        if (!Files.exists(filePath)) {
            createFile(filePath);
            return new ArrayList<>(); // Return an empty list for a newly created file
        }

        try {
            List<Data> dataList = new ArrayList<>();
            Files.lines(filePath).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String subject = parts[0].replaceAll("\"", "").trim();
                    String description = parts[1].replaceAll("\"", "").trim();
                    dataList.add(new Data(subject, description));
                } else {
                    // Handle invalid format or throw an exception
                    throw new IllegalArgumentException("Invalid data format: " + line);
                }
            });

            return dataList;
        } catch (IOException e) {
            throw new IOException("Failed to read from the file: " + fileName, e);
        }
    }

    @Override
    public void writeFile(String fileName, List<Data> dataList) throws IOException {
        Path filePath = Paths.get(dataPath, fileName);
        createFile(filePath);

        try {
            Files.write(filePath, dataListToStringList(dataList), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new IOException("Failed to write to the file: " + fileName, e);
        }
    }

    private List<String> dataListToStringList(List<Data> dataList) {
        List<String> stringList = new ArrayList<>();
        for (Data data : dataList) {
            String line = String.format("\"%s\",\"%s\"", data.getSubject(), data.getDescription());
            stringList.add(line);
        }
        return stringList;
    }

    private void createFile(Path filePath) throws IOException {
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }
}
