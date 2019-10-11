package com.homurax.chapter09.common.data;

import lombok.Data;

@Data
public class PersonPair extends Person {

    private String otherId;

    public String getFullId() {
        return getId() + "," + otherId;
    }

}
