package com.homurax.chapter09.common.serial;

import com.homurax.chapter09.common.data.Person;
import com.homurax.chapter09.common.data.PersonPair;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SerialSocialNetwork {

    public static List<PersonPair> bidirectionalCommonContacts(List<Person> people) {

        long start, end;

        start = System.currentTimeMillis();
        Map<String, List<PersonPair>> group = people
                .stream()
                .map(new CommonPersonMapper())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(PersonPair::getFullId));
        end = System.currentTimeMillis();
        System.out.println("Time 0:" + (end - start));

        Collector<Collection<String>, Collection<String>, Collection<String>> intersecting = Collector.of(
                ArrayList::new,
                (acc, list) -> {
                    if (acc.isEmpty()) {
                        acc.addAll(list);
                    } else {
                        acc.retainAll(list);
                    }
                },
                (acc1, acc2) -> {
                    if (acc1.isEmpty())
                        return acc2;
                    if (acc2.isEmpty())
                        return acc1;
                    acc1.retainAll(acc2);
                    return acc1;
                },
                (acc) -> acc);

        start = System.currentTimeMillis();
        List<PersonPair> peopleCommonContacts = group.entrySet()
                .stream()
                .map((entry) -> {
                    Collection<String> commonContacts = entry.getValue()
                                    .stream()
                                    .map(Person::getContacts)
                                    .collect(intersecting);

                    PersonPair person = new PersonPair();
                    person.setId(entry.getKey().split(",")[0]);
                    person.setOtherId(entry.getKey().split(",")[1]);
                    person.setContacts(new ArrayList<>(commonContacts));
                    return person;
                }).collect(Collectors.toList());
        end = System.currentTimeMillis();
        System.out.println("Time 1:" + (end - start));

        return peopleCommonContacts;
    }

}
