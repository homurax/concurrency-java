package com.homurax.chapter11.structure.compare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
	
	private int id;
	private String threadName;
	private Date date;
}
