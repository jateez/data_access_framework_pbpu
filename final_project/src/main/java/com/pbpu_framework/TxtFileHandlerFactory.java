package com.pbpu_framework;

import java.util.List;

public class TxtFileHandlerFactory implements DataFileHandlerFactory {
    @Override
    public FileTypeHandler<List<Data>> createFileHandler() {
        return new FileTypeHandler<>(new TxtFileHandler());
    }
}
