package com.homurax.chapter05.indexing.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document {

    private String fileName;
    private Map<String, Integer> voc;
}
