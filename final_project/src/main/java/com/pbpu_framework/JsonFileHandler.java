package com.pbpu_framework;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonFileHandler implements DataFileHandler<List<Data>> {

    private final String dataPath = "data/";

    @Override
    public List<Data> readFile(String fileName) throws IOException {
        try (FileReader reader = new FileReader(dataPath + fileName)) {
            Type type = new TypeToken<List<Data>>() {
            }.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            throw new IOException("Failed to read from the file: " + fileName, e);
        }
    }

    @Override
    public void writeFile(String fileName, List<Data> dataList) throws IOException {
        try (FileWriter writer = new FileWriter(dataPath + fileName)) {
            new Gson().toJson(dataList, writer);
        } catch (IOException e) {
            throw new IOException("Failed to write to the file: " + fileName, e);
        }
    }
}
