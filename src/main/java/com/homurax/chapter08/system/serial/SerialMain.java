package com.homurax.chapter08.system.serial;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

public class SerialMain {

    public static void main(String[] args) throws IOException {


        String[] query1 = {"james", "bond"};
        String[] query2 = {"gone", "with", "the", "wind"};
        String[] query3 = {"rocky"};
        StringBuilder bufferResults = new StringBuilder();

        long start, end;

        bufferResults.append("Version 1, query 1, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.basicSearch(query1);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 1, query 2, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.basicSearch(query2);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 1, query 3, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.basicSearch(query3);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 2, query 1, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.reducedSearch(query1);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 2, query 2, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.reducedSearch(query2);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 2, query 3, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.reducedSearch(query3);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 3, query 1, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.htmlSearch(query1, "query1_concurrent");
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 3, query 2, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.htmlSearch(query2, "query2_concurrent");
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 3, query 3, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.htmlSearch(query3, "query3_concurrent");
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");


        SerialInvertedIndex invertedIndex = new SerialInvertedIndex();
        SerialFileLoader loader = new SerialFileLoader();
        invertedIndex = loader.load(Paths.get("src\\main\\java\\com\\homurax\\chapter08\\system\\index", "invertedIndex.txt"));

        bufferResults.append("Version 4, query 1, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.preloadSearch(query1, invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 4, query 2, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.preloadSearch(query2, invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 4, query 3, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.preloadSearch(query3, invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        ForkJoinPool pool = new ForkJoinPool();


        bufferResults.append("Version 5, query 1, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.executorSearch(query1, invertedIndex, pool);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 5, query 2, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.executorSearch(query2, invertedIndex, pool);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Version 5, query 3, concurrent\n");
        start = System.currentTimeMillis();
        SerialSearch.executorSearch(query3, invertedIndex, pool);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Inverted Index Data: Words in File version 1\n");
        start = System.currentTimeMillis();
        SerialData.getWordsInFile1("27759897.txt", invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Inverted Index Data: Words in File version 2\n");
        start = System.currentTimeMillis();
        SerialData.getWordsInFile2("27759897.txt", invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Inverted Index Data: Average Tfxidf in File \n");
        start = System.currentTimeMillis();
        SerialData.getAverageTfxidf("27759897.txt", invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Inverted Index Data: Max Tfxidf\n");
        start = System.currentTimeMillis();
        SerialData.maxTfxidf(invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        bufferResults.append("Inverted Index Data: Min Tfxidf\n");
        start = System.currentTimeMillis();
        SerialData.minTfxidf(invertedIndex);
        end = System.currentTimeMillis();
        bufferResults.append("Execution Time: ").append(end - start).append("\n");

        System.out.println("***************************");
        System.out.println(bufferResults.toString());
        System.out.println("***************************");

    }
}
