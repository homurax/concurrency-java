package com.homurax.chapter02.search.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Result {

    private String path;
    private boolean found = false;
}
