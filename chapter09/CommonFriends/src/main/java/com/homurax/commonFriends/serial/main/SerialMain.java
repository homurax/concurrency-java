package com.homurax.commonFriends.serial.main;

import com.homurax.commonFriends.data.DataLoader;
import com.homurax.commonFriends.data.Person;
import com.homurax.commonFriends.data.PersonPair;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SerialMain {

    public static void main(String[] args) {

        System.out.println("Serial Main Bidirectional - Test");
        List<Person> people = DataLoader.load("data", "test.txt");

        LocalDateTime start = LocalDateTime.now();
        List<PersonPair> peopleCommonContacts = SerialSocialNetwork.bidirectionalCommonContacts(people);
        LocalDateTime end = LocalDateTime.now();
        peopleCommonContacts.forEach(p -> System.out.println(p.getFullId() + ": " + formatContacts(p.getContacts())));
        System.out.println("Execution Time: " + Duration.between(start, end));

        System.out.println("Serial Main Bidirectional - Facebook");
        people = DataLoader.load("data", "facebook_contacts.txt");
        start = LocalDateTime.now();
        peopleCommonContacts = SerialSocialNetwork.bidirectionalCommonContacts(people);
        end = LocalDateTime.now();
        peopleCommonContacts.forEach(p -> System.out.println(p.getFullId() + ": " + formatContacts(p.getContacts())));
        System.out.println("Execution Time: " + Duration.between(start, end));

    }

    private static String formatContacts(List<String> contacts) {
        StringBuilder buffer = new StringBuilder();
        for (String contact : contacts) {
            buffer.append(contact).append(",");
        }
        return buffer.toString();
    }

}
