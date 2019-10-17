package com.homurax.chapter11.structure.hash.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasicReview {
	
	private String user;
	private short value;
	private String date;
}
