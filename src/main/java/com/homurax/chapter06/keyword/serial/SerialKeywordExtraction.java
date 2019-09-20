package com.homurax.chapter06.keyword.serial;

import com.homurax.chapter06.keyword.common.Document;
import com.homurax.chapter06.keyword.common.DocumentParser;
import com.homurax.chapter06.keyword.common.Keyword;
import com.homurax.chapter06.keyword.common.Word;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

public class SerialKeywordExtraction {

	public static void main(String[] args) {

		File source = new File("src\\main\\java\\com\\homurax\\chapter05\\indexing\\data");
		File[] files = source.listFiles();
		Map<String, Word> globalVoc = new HashMap<>();
		Map<String, Integer> globalKeywords = new HashMap<>();
		int totalCalls = 0;
		int numDocuments = 0;

		long start = System.currentTimeMillis();

		// Phase 1: Parse all the documents
		if (files == null) {
			System.err.println("Unable to read the 'data' folder");
			return;
		}

		for (File file : files) {
			if (file.getName().endsWith(".txt")) {
				Document doc = DocumentParser.parse(file.getAbsolutePath());
				for (Word word : doc.getVoc().values()) {
					globalVoc.merge(word.getWord(), word, Word::merge);
				}
				numDocuments++;
			}
		}
		System.out.println("Corpus: " + numDocuments + " documents.");

		// Phase 2: Update the df of the voc of the Documents
		for (File file : files) {
			if (file.getName().endsWith(".txt")) {
				Document doc = DocumentParser.parse(file.getAbsolutePath());
				List<Word> keywords = new ArrayList<>(doc.getVoc().values());
				for (Word word : keywords) {
					Word globalWord = globalVoc.get(word.getWord());
					word.setDf(globalWord.getDf(), numDocuments);
				}
				Collections.sort(keywords);

				if (keywords.size() > 10) {
					keywords = keywords.subList(0, 10);
				}
				
				for (Word word : keywords) {
					addKeyword(globalKeywords, word.getWord());
					totalCalls++;
				}
			}
		}

		// Phase 3: Get a list of a better keywords
		List<Keyword> orderedGlobalKeywords = new ArrayList<>();
		for (Entry<String, Integer> entry : globalKeywords.entrySet()) {
			Keyword keyword = new Keyword(entry.getKey(), entry.getValue());
			orderedGlobalKeywords.add(keyword);
		}

		Collections.sort(orderedGlobalKeywords);

		if (orderedGlobalKeywords.size() > 100) {
			orderedGlobalKeywords = orderedGlobalKeywords.subList(0, 100);
		}

		for (Keyword keyword : orderedGlobalKeywords) {
			System.out.println(keyword.getWord() + ": " + keyword.getDf());
		}
		long end = System.currentTimeMillis();

		System.out.println("Execution Time: " + (end - start));
		System.out.println("Vocabulary Size: " + globalVoc.size());
		System.out.println("Keyword Size: " + globalKeywords.size());
		System.out.println("Number of Documents: " + numDocuments);
		System.out.println("Total calls: " + totalCalls);

	}

	private static void addKeyword(Map<String, Integer> globalKeywords, String word) {
		globalKeywords.merge(word, 1, Integer::sum);
	}

}
