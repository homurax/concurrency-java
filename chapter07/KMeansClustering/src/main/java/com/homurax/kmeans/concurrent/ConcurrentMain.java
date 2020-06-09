package com.homurax.kmeans.concurrent;

import com.homurax.kmeans.common.data.VocabularyLoader;
import com.homurax.kmeans.serial.Document;
import com.homurax.kmeans.serial.DocumentCluster;
import com.homurax.kmeans.serial.DocumentLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ConcurrentMain {

    public static void main(String[] args) throws IOException {

        Path pathVoc = Paths.get("data\\movies.words");
        Map<String, Integer> vocIndex = VocabularyLoader.load(pathVoc);
        System.out.println("Voc Size: " + vocIndex.size());

        Path pathDocs = Paths.get("data\\movies.data");
        Document[] documents = DocumentLoader.load(pathDocs, vocIndex);
        System.out.println("Document Size: " + documents.length);

        if (args.length != 3) {
            System.err.println("Please specify K, SEED, MIN_SIZE");
            return;
        }
        int K = Integer.parseInt(args[0]);
        int SEED = Integer.parseInt(args[1]);
        int MAX_SIZE = Integer.parseInt(args[2]);


        LocalDateTime start = LocalDateTime.now();
        DocumentCluster[] clusters = ConcurrentKMeans.calculate(documents, K, vocIndex.size(), SEED, MAX_SIZE);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);

        System.out.println("K: " + K + "; SEED: " + SEED + "; MAX_SIZE: " + MAX_SIZE);
        System.out.println("Execution Time: " + duration);

        String collect = Arrays.stream(clusters)
                .map(DocumentCluster::getDocumentCount)
                .sorted(Comparator.reverseOrder())
                .map(Object::toString)
                .collect(Collectors.joining(", ", "Cluster sizes: ", ""));

        System.out.println(collect);
    }

}
