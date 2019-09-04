package com.homurax.chapter03.nearest.knn.loader;

import com.homurax.chapter03.nearest.knn.data.BankMarketing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that loads the examples of the Bank Marketing data set from a file
 */
public class BankMarketingLoader {

    public List<BankMarketing> load(String dataPath) {
        Path file = Paths.get(dataPath);
        List<BankMarketing> dataList = new ArrayList<>();
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                BankMarketing bankMarketing = new BankMarketing();
                bankMarketing.setData(data);
                dataList.add(bankMarketing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
