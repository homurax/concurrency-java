package com.homurax.bestMatching.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BestMatchingData {

    private int distance;

    private List<String> words;
}
