package com.homurax.newsNotification.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
