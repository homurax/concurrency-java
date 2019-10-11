package com.homurax.chapter09.common.data;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Person> load(String path, String fileName) {

        Path file = Paths.get(path, fileName);
        List<Person> dataSet = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("-");
                Person person = new Person();
                person.setId(data[0].intern());
                data = data[1].split(",");
                for (String contact : data) {
                    person.addContact(contact.intern());
                }
                dataSet.add(person);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return dataSet;

    }

}
