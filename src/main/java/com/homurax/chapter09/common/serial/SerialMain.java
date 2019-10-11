package com.homurax.chapter09.common.serial;

import com.homurax.chapter09.common.data.DataLoader;
import com.homurax.chapter09.common.data.Person;
import com.homurax.chapter09.common.data.PersonPair;

import java.util.List;

public class SerialMain {

    public static void main(String[] args) {

        System.out.println("Serial Main Bidirectional - Test");
        List<Person> people = DataLoader.load("data", "test.txt");
        long start = System.currentTimeMillis();
        List<PersonPair> peopleCommonContacts = SerialSocialNetwork.bidirectionalCommonContacts(people);
        long end = System.currentTimeMillis();
        peopleCommonContacts.forEach(p -> System.out.println(p.getFullId() + ": " + formatContacts(p.getContacts())));
        System.out.println("Execution Time: " + (end - start));

        System.out.println("Serial Main Bidirectional - Facebook");
        people = DataLoader.load("data", "facebook_contacts.txt");
        start = System.currentTimeMillis();
        SerialSocialNetwork.bidirectionalCommonContacts(people);
        end = System.currentTimeMillis();
        System.out.println("Execution Time: " + (end - start));

    }

    private static String formatContacts(List<String> contacts) {
        StringBuilder buffer = new StringBuilder();
        for (String contact : contacts) {
            buffer.append(contact).append(",");
        }
        return buffer.toString();
    }

}
