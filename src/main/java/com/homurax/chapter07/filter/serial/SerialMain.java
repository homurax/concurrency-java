package com.homurax.chapter07.filter.serial;

import com.homurax.chapter07.filter.common.CensusData;
import com.homurax.chapter07.filter.common.CensusDataLoader;
import com.homurax.chapter07.filter.common.FilterData;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SerialMain {

    public static void main(String[] args) {

        Path path = Paths.get("src\\main\\java\\com\\homurax\\chapter07\\filter\\data", "census-income.data");

        CensusData[] data = CensusDataLoader.load(path);
        System.out.println("Number of items: " + data.length);

        long start, end;

        // Test 1: findFirst, exists, in the first ones
        List<FilterData> filters = new ArrayList<>();
        FilterData filter = new FilterData();
        filter.setIdField(32);
        filter.setValue("Dominican-Republic");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(31);
        filter.setValue("Dominican-Republic");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(1);
        filter.setValue("Not in universe");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(14);
        filter.setValue("Not in universe");
        filters.add(filter);

        start = System.currentTimeMillis();
        CensusData result = SerialSearch.findAny(data, filters);
        System.out.println("Test 1 - Result: " + result.getReasonForUnemployment());
        end = System.currentTimeMillis();
        System.out.println("Test 1- Execution Time: " + (end - start));

        // Test 2: findFirst, exists, in the last ones
        filters = new ArrayList<>();
        filter = new FilterData();
        filter.setIdField(32);
        filter.setValue("United-States");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(31);
        filter.setValue("Greece");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(1);
        filter.setValue("Private");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(14);
        filter.setValue("Not in universe");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(0);
        filter.setValue("62");
        filters.add(filter);

        start = System.currentTimeMillis();
        result = SerialSearch.findAny(data, filters);
        System.out.println("Test 2 - Result: " + result.getReasonForUnemployment());
        end = System.currentTimeMillis();
        System.out.println("Test 2- Execution Time: " + (end - start));

        // Test 3 Doesn't exists
        filters = new ArrayList<>();
        filter = new FilterData();
        filter.setIdField(32);
        filter.setValue("XXXX");
        filters.add(filter);

        start = System.currentTimeMillis();
        result = SerialSearch.findAny(data, filters);
        if (result == null) {
            System.out.println("Test 3 - Result: " + result);
        } else {
            System.out.println("Test 3 - Result: " + result.getReasonForUnemployment());
        }
        end = System.currentTimeMillis();
        System.out.println("Test 3 - Execution Time: " + (end - start));

        // Test 4: Error Individual
        filters = new ArrayList<>();
        filter = new FilterData();
        filter.setIdField(0);
        filter.setValue("Dominican-Republic");
        filters.add(filter);

        start = System.currentTimeMillis();
        try {
            result = SerialSearch.findAny(data, filters);
            System.out.println("Test 4 - Results: " + result.getCitizenship());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        end = System.currentTimeMillis();
        System.out.println("Test 4 - Execution Time: " + (end - start));

        // Test 5: List
        filters = new ArrayList<>();
        filter = new FilterData();
        filter.setIdField(32);
        filter.setValue("Dominican-Republic");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(31);
        filter.setValue("Dominican-Republic");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(1);
        filter.setValue("Not in universe");
        filters.add(filter);
        filter = new FilterData();
        filter.setIdField(14);
        filter.setValue("Not in universe");
        filters.add(filter);

        start = System.currentTimeMillis();
        List<CensusData> results = SerialSearch.findAll(data, filters);
        System.out.println("Test 5 - Results: " + results.size());
        end = System.currentTimeMillis();
        System.out.println("Test 5 - Execution Time: " + (end - start));

        // Test 6: Error List
        filters = new ArrayList<FilterData>();
        filter = new FilterData();
        filter.setIdField(0);
        filter.setValue("Dominican-Republic");
        filters.add(filter);

        start = System.currentTimeMillis();
        try {
            results = SerialSearch.findAll(data, filters);
            System.out.println("Test 6 - Results: " + results.size());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        end = System.currentTimeMillis();
        System.out.println("Test 6 - Execution Time: " + (end - start));

    }

}
