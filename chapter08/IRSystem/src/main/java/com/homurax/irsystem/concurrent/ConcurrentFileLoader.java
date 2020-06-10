package com.homurax.irsystem.concurrent;

import com.homurax.irsystem.common.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Stream;

public class ConcurrentFileLoader {

    public ConcurrentInvertedIndex load(Path path) throws IOException {
        ConcurrentInvertedIndex invertedIndex = new ConcurrentInvertedIndex();
        ConcurrentLinkedDeque<Token> results = new ConcurrentLinkedDeque<>();

        try (Stream<String> fileStream = Files.lines(path)) {
            fileStream.parallel()
                    .flatMap(ConcurrentSearch::limitedMapper)
                    .forEach(results::add);
        }

        invertedIndex.setIndex(new ArrayList<>(results));
        return invertedIndex;
    }
}
