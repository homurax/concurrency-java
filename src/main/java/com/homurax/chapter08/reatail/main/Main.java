package com.homurax.chapter08.reatail.main;

import com.homurax.chapter08.reatail.common.Record;
import com.homurax.chapter08.reatail.concurrent.data.ConcurrentStatistics;
import com.homurax.chapter08.reatail.serial.data.SerialDataLoader;
import com.homurax.chapter08.reatail.serial.data.SerialStatistics;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	private static Map<String, List<Double>> totalTimes = new LinkedHashMap<>();
	private static List<Record> records;

	private static void measure(String name, Runnable r) {
		long start = System.nanoTime();
		r.run();
		long end = System.nanoTime();
		totalTimes.computeIfAbsent(name, k -> new ArrayList<>()).add((end - start) / 1_000_000.0);
	}

	public static void main(String[] args) throws IOException {

		Path path = Paths.get("src\\main\\java\\com\\homurax\\chapter08\\reatail\\data", "Online_Retail.csv");
		records = SerialDataLoader.load(path);

		for (int i = 0; i < 10; i++) {
			measure("Serial Customers from UnitedKingdom", () -> SerialStatistics.customersFromUnitedKingdom(records));
			measure("Serial Quantity from UnitedKingdom", () -> SerialStatistics.quantityFromUnitedKingdom(records));
			measure("Serial Countries for Product", () -> SerialStatistics.countriesForProduct(records));
			measure("Serial Quantity for Product", () -> SerialStatistics.quantityForProductOk(records));
			measure("Serial Multiple Filter for Products", () -> SerialStatistics.multipleFilterData(records));
			measure("Serial Multiple Filter for Products with Predicate", () -> SerialStatistics.multipleFilterDataPredicate(records));
			measure("Serial Biggest Invoice Amount", () -> SerialStatistics.getBiggestInvoiceAmounts(records));
			measure("Serial Products Between 1 and 10", () -> SerialStatistics.productsBetween1and10(records));
		}

		for (int i = 0; i < 10; i++) {
			measure("Customers from UnitedKingdom", () -> ConcurrentStatistics.customersFromUnitedKingdom(records));
			measure("Quantity from UnitedKingdom", () -> ConcurrentStatistics.quantityFromUnitedKingdom(records));
			measure("Countries for Product", () -> ConcurrentStatistics.countriesForProduct(records));
			measure("Quantity for Product", () -> ConcurrentStatistics.quantityForProductOk(records));
			measure("Multiple Filter for Products", () -> ConcurrentStatistics.multipleFilterData(records));
			measure("Multiple Filter for Products with Predicate", () -> ConcurrentStatistics.multipleFilterDataPredicate(records));
			measure("Biggest Invoice Amount", () -> ConcurrentStatistics.getBiggestInvoiceAmounts(records));
			measure("Products Between 1 and 10", () -> ConcurrentStatistics.productsBetween1and10(records));
		}
		
		
		totalTimes.forEach((name, times) -> System.out.printf("%25s: %s [avg: %6.2f] ms%n", name,
				times.stream().map(t -> String.format("%6.2f", t)).collect(Collectors.joining(" ")),
				times.stream().mapToDouble(Double::doubleValue).average().getAsDouble()));
	}

}
