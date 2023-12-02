package com.pbpu_framework;

import java.io.IOException;

public interface DataFileHandler<Type> {
    Type readFile(String fileName) throws IOException;

    void writeFile(String fileName, Type data) throws IOException;
}
