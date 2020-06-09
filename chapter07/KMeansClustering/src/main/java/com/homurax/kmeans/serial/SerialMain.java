package com.homurax.kmeans.serial;

import com.homurax.kmeans.common.data.VocabularyLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class SerialMain {

    public static void main(String[] args) throws IOException {

        Path pathVoc = Paths.get("data", "movies.words");
        Map<String, Integer> vocIndex = VocabularyLoader.load(pathVoc);
        System.out.println("Voc Size: " + vocIndex.size());

        Path pathDocs = Paths.get("data", "movies.data");
        Document[] documents = DocumentLoader.load(pathDocs, vocIndex);
        System.out.println("Document Size: " + documents.length);

        if (args.length != 2) {
            System.err.println("Please specify K and SEED");
            return;
        }
        int K = Integer.parseInt(args[0]);
        int SEED = Integer.parseInt(args[1]);

        LocalDateTime start = LocalDateTime.now();
        DocumentCluster[] clusters = SerialKMeans.calculate(documents, K, vocIndex.size(), SEED);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("K: " + K + "; SEED: " + SEED);
        System.out.println("Execution Time: " + duration);


        String collect = Arrays.stream(clusters)
                .map(DocumentCluster::getDocumentCount)
                .sorted(Comparator.reverseOrder())
                .map(Object::toString)
                .collect(Collectors.joining(", ", "Cluster sizes: ", ""));

        System.out.println(collect);

    }

}
