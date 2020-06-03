package com.homurax.simpleserver.wdi.data;

import com.homurax.simpleserver.common.Constants;
import com.homurax.simpleserver.wdi.loader.WDILoader;

import java.io.StringWriter;
import java.util.List;

public class WDIDAO {

    private static WDIDAO dao;
    private final List<WDI> data;


    private WDIDAO(String route) {
        WDILoader loader = new WDILoader();
        data = loader.load(route);
    }

    public static WDIDAO getDAO() {
        if (dao == null) {
            dao = new WDIDAO(Constants.DATA_ROUTE);
        }
        return dao;
    }


    public String query(String codCountry, String codIndicator) {

        WDI wdi = null;
        for (WDI datum : data) {
            wdi = datum;
            if ((wdi.getCountryCode().equals(codCountry)) && (wdi.getIndicatorCode().equals(codIndicator))) {
                break;
            }
        }

        StringWriter writer = new StringWriter();
        writer.write(codCountry);
        writer.write(";");
        writer.write(codIndicator);
        writer.write(";");
        Double[] years = wdi.getValues();
        for (int i = 0; i < years.length; i++) {
            writer.write(years[i].toString());
            if (i < years.length - 1) {
                writer.write(";");
            }
        }
        return writer.toString();
    }


    public String query(String codCountry, String codIndicator, short year) throws Exception {

        System.err.println("Query: " + codCountry + ", " + codIndicator);
        WDI wdi = null;
        for (WDI datum : data) {
            wdi = datum;
            if ((wdi.getCountryCode().equals(codCountry)) && (wdi.getIndicatorCode().equals(codIndicator))) {
                break;
            }
        }

        StringWriter writer = new StringWriter();
        writer.write(codCountry);
        writer.write(";");
        writer.write(codIndicator);
        writer.write(";");
        writer.write("" + year);
        writer.write(";");
        writer.write(wdi.getValue(year).toString());
        return writer.toString();
    }


    public String report(String codIndicator) {

        StringWriter writer = new StringWriter();
        writer.write(codIndicator);
        writer.write(";");
        for (WDI wdi : data) {
            if (wdi.getIndicatorCode().equals(codIndicator)) {
                Double[] years = wdi.getValues();
                double mean = 0.0;
                for (Double year : years) {
                    mean += year;
                }
                mean /= years.length;
                writer.write(wdi.getCountryCode());
                writer.write(";");
                writer.write("" + mean);
                writer.write(";");
            }
        }

        return writer.toString();

    }

    public List<WDI> getData() {
        return data;
    }

}
