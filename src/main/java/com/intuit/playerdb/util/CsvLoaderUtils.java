package com.intuit.playerdb.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvLoaderUtils {
    private static final CsvMapper mapper = new CsvMapper();
    public static <T> List<T> read(Class<T> type, String fileName) throws IOException {
        CsvSchema schema = mapper.schemaFor(type).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(type).with(schema);
        InputStream file = new ClassPathResource(fileName).getInputStream();
        return reader.<T>readValues(file).readAll();
    }
}
