package com.homurax.irsystem.serial;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;

public class SerialMain {

	public static void main(String[] args) throws IOException {

		String[] query1 = { "james", "bond" };
		String[] query2 = { "gone", "with", "the", "wind" };
		String[] query3 = { "rocky" };
		StringBuilder bufferResults = new StringBuilder();
		

		bufferResults.append("Version 1, query 1, concurrent\n");
		LocalDateTime start = LocalDateTime.now();
		SerialSearch.basicSearch(query1);
		LocalDateTime end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start,end)).append("\n");

		bufferResults.append("Version 1, query 2, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.basicSearch(query2);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start,end)).append("\n");

		bufferResults.append("Version 1, query 3, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.basicSearch(query3);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 2, query 1, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.reducedSearch(query1);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 2, query 2, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.reducedSearch(query2);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 2, query 3, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.reducedSearch(query3);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 3, query 1, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.htmlSearch(query1, "query1_concurrent");
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 3, query 2, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.htmlSearch(query2, "query2_concurrent");
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 3, query 3, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.htmlSearch(query3, "query3_concurrent");
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		
		SerialInvertedIndex invertedIndex = new SerialInvertedIndex();
		SerialFileLoader loader = new SerialFileLoader();
		invertedIndex = loader.load(Paths.get("index","invertedIndex.txt"));
	
		bufferResults.append("Version 4, query 1, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.preloadSearch(query1, invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 4, query 2, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.preloadSearch(query2, invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 4, query 3, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.preloadSearch(query3, invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");
		
		ForkJoinPool pool = new ForkJoinPool();

		
		bufferResults.append("Version 5, query 1, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.executorSearch(query1, invertedIndex, pool);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Version 5, query 2, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.executorSearch(query2, invertedIndex, pool);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start,end)).append("\n");

		bufferResults.append("Version 5, query 3, concurrent\n");
		start = LocalDateTime.now();
		SerialSearch.executorSearch(query3, invertedIndex, pool);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");
	
		bufferResults.append("Inverted Index Data: Words in File version 1\n");
		start = LocalDateTime.now();
		SerialData.getWordsInFile1("27759897.txt", invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Inverted Index Data: Words in File version 2\n");
		start = LocalDateTime.now();
		SerialData.getWordsInFile2("27759897.txt", invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");
		
		bufferResults.append("Inverted Index Data: Average Tfxidf in File \n");
		start = LocalDateTime.now();
		SerialData.getAverageTfxidf("27759897.txt",invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start, end)).append("\n");

		bufferResults.append("Inverted Index Data: Max Tfxidf\n");
		start = LocalDateTime.now();
		SerialData.maxTfxidf(invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start,end)).append("\n");
		
		bufferResults.append("Inverted Index Data: Min Tfxidf\n");
		start = LocalDateTime.now();
		SerialData.minTfxidf(invertedIndex);
		end = LocalDateTime.now();
		bufferResults.append("Execution Time: ").append(Duration.between(start,end)).append("\n");

		System.out.println("***************************");
		System.out.println(bufferResults.toString());
		System.out.println("***************************");

	}
}
