package com.homurax.chapter03.server.wdi.loader;

import com.homurax.chapter03.server.wdi.data.WDI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WDILoader {

    public List<WDI> load(String route) {
        Path file = Paths.get(route);
        List<WDI> dataList = new ArrayList<>();
        int lineNumber = 1;
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            // First line are headers
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = parse(line);
                WDI wdi = new WDI();
                wdi.setData(data);
                dataList.add(wdi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private String[] parse(String line) {
        String[] result = new String[59];
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        boolean comillas = false;
        for (int i = 0; i < line.length(); i++) {
            char temp = line.charAt(i);
            if (temp == '"') {
                comillas = !comillas;
            } else if ((temp == ',') && (!comillas)) {
                result[index++] = buffer.toString();
                buffer = new StringBuffer();
            } else {
                buffer.append(temp);
            }
        }
        result[index] = buffer.toString();
        return result;
    }
}
