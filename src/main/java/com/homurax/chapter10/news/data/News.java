package com.homurax.chapter10.news.data;

import lombok.Data;

import java.util.Date;

@Data
public class News {

    public static final int SPORTS = 0;
    public static final int WORLD = 1;
    public static final int ECONOMIC = 2;
    public static final int SCIENCE = 3;

    private int category;
    private String txt;
    private Date date;
}
