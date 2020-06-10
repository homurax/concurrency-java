package com.homurax.numericalSummarization.serial.data;

import com.homurax.numericalSummarization.common.Record;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class SerialDataLoader {

    public static List<Record> load(Path path) throws IOException {

        System.out.println("Loading data");

        List<String> lines = Files.readAllLines(path);

        return lines
                .stream()
                .skip(1)
                .map(l -> l.split(";"))
                .map(Record::new)
                .collect(Collectors.toList());
    }
}
