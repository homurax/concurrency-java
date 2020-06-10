package com.homurax.commonFriends.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person {

    private String id;
    private List<String> contacts;

    public Person() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(String contact) {
        contacts.add(contact);
    }
}
