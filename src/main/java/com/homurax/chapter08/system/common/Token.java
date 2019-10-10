package com.homurax.chapter08.system.common;

import lombok.Getter;

@Getter
public class Token {

    private final String word;
    private final double tfxidf;
    private final String file;

    public Token(String word, String token) {
		String[] parts = token.split(":");
        this.word = word;
        this.file = parts[0];
        this.tfxidf = Double.parseDouble(parts[1]);
    }

    @Override
    public String toString() {
        return word + ":" + file + ":" + tfxidf;
    }


}
