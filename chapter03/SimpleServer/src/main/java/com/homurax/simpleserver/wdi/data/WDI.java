package com.homurax.simpleserver.wdi.data;

import lombok.Data;

@Data
public class WDI {

    public final static short FIRST_YEAR = 1960;

    private String countryName;
    private String countryCode;
    private String indicatorName;
    private String indicatorCode;
    private Double[] values;

    public void setData(String[] data) throws Exception {
        if (data.length != 59) {
            throw new Exception("Data length is not correct: " + data.length);
        }
        values = new Double[55];
        countryName = getString(data[0]);
        countryCode = getString(data[1]);
        indicatorName = getString(data[2]);
        indicatorCode = getString(data[3]);
        for (int i = 0; i < 55; i++) {
            values[i] = getValue(data[i + 4]);
        }
    }

    private String getString(String string) {
        if (string.startsWith("\"")) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    private Double getValue(String string) {
        if (string.trim().length() == 0) {
            return 0.0d;
        }
        return Double.valueOf(string);
    }

    public Double getValue(short year) throws Exception {
        if ((year < FIRST_YEAR) || (year >= FIRST_YEAR + values.length)) {
            throw new Exception("No data for that year");
        }
        short index = (short) (year - FIRST_YEAR);
        return values[index];
    }
}
