package com.homurax.chapter09.common.concurrent;

import com.homurax.chapter09.common.data.DataLoader;
import com.homurax.chapter09.common.data.Person;
import com.homurax.chapter09.common.data.PersonPair;

import java.util.List;

public class ConcurrentMain {

    public static void main(String[] args) {

        long start, end;
        System.out.println("Concurrent Main Bidirectional - Test");
        List<Person> people = DataLoader.load("data", "test.txt");
        start = System.currentTimeMillis();
        List<PersonPair> peopleCommonContacts = ConcurrentSocialNetwork.bidirectionalCommonContacts(people);
        end = System.currentTimeMillis();

        peopleCommonContacts.forEach(p -> System.out.println(p.getFullId() + ": " + formatContacts(p.getContacts())));
        System.out.println("Execution Time: " + (end - start));

        System.out.println("Concurrent Main Bidirectional - Facebook");
        people = DataLoader.load("data", "facebook_contacts.txt");
        start = System.currentTimeMillis();
        ConcurrentSocialNetwork.bidirectionalCommonContacts(people);
        end = System.currentTimeMillis();

        System.out.println("Execution Time: " + (end - start));
    }

    private static String formatContacts(List<String> contacts) {
        StringBuilder buffer = new StringBuilder();
        for (String contact : contacts) {
            buffer.append(contact + ",");
        }
        return buffer.toString();
    }

}
