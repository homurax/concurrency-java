package com.homurax.chapter09.common.serial;

import com.homurax.chapter09.common.data.Person;
import com.homurax.chapter09.common.data.PersonPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CommonPersonMapper implements Function<Person, List<PersonPair>> {

    @Override
    public List<PersonPair> apply(Person person) {

        List<PersonPair> result = new ArrayList<>();

        List<String> contacts = person.getContacts();
        Collections.sort(contacts);

        for (String contact : contacts) {
            PersonPair personExt = new PersonPair();
            if (person.getId().compareTo(contact) < 0) {
                personExt.setId(person.getId());
                personExt.setOtherId(contact);
            } else {
                personExt.setId(contact);
                personExt.setOtherId(person.getId());
            }
            personExt.setContacts(contacts);
			result.add(personExt);
        }

        return result;
    }

}
