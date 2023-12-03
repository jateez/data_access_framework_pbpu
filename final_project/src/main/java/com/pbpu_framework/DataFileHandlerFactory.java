package com.pbpu_framework;

import java.util.List;

public interface DataFileHandlerFactory {
    FileTypeHandler<List<Data>> createFileHandler();
}
