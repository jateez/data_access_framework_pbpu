package com.pbpu_framework;

import java.io.IOException;

public class FileTypeHandler<Type> {
    private DataFileHandler<Type> fileHandler;

    public FileTypeHandler(DataFileHandler<Type> fileHandler) {
        this.fileHandler = fileHandler;
    }

    public Type readFile(String fileName) throws IOException {
        return fileHandler.readFile(fileName);
    }

    public void writeFile(String fileName, Type data) throws IOException {
        fileHandler.writeFile(fileName, data);
    }
}
