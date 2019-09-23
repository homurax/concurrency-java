package com.homurax.chapter07.filter.serial;

import com.homurax.chapter07.filter.common.CensusData;
import com.homurax.chapter07.filter.common.Filter;
import com.homurax.chapter07.filter.common.FilterData;

import java.util.ArrayList;
import java.util.List;

public class SerialSearch {

    public static CensusData findAny(CensusData[] data, List<FilterData> filters) {

        int index = 0;
        for (CensusData censusData : data) {
            if (Filter.filter(censusData, filters)) {
                System.out.println("Found: " + index);
                return censusData;
            }
            index++;
        }
        return null;
    }

    public static List<CensusData> findAll(CensusData[] data, List<FilterData> filters) {

        List<CensusData> results = new ArrayList<>();
        for (CensusData censusData : data) {
            if (Filter.filter(censusData, filters)) {
                results.add(censusData);
            }
        }
        return results;
    }

}
