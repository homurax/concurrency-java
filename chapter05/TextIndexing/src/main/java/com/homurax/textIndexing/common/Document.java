package com.homurax.textIndexing.common;

import lombok.Data;

import java.util.Map;

@Data
public class Document {

    private String fileName;
    private Map<String, Integer> voc;
}
