package com.pbpu_framework;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializedObjectFileHandler implements DataFileHandler<List<Data>> {

    private final String dataPath = "data/";

    @Override
    public List<Data> readFile(String fileName) throws IOException {
        List<Data> dataList = new ArrayList<>();

        if (!new File(dataPath + fileName).exists()) {
            // Create empty file if it doesn't exist
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataPath + fileName))) {
                oos.writeObject(dataList);
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataPath + fileName))) {
            // Read the object and cast it directly to List<Data>
            Object object = ois.readObject();
            if (object instanceof List) {
                dataList = (List<Data>) object;
            }
        } catch (EOFException e) {
            // EOFException indicates the end of the file
        } catch (ClassNotFoundException e) {
            throw new IOException("Error reading serialized data: " + e.getMessage(), e);
        }

        return dataList;
    }

    @Override
    public void writeFile(String fileName, List<Data> dataList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataPath + fileName))) {
            oos.writeObject(dataList);
        }
    }

}
