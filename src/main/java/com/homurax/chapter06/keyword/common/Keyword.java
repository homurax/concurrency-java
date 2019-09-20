package com.homurax.chapter06.keyword.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Keyword implements Comparable<Keyword> {

	private String word;
	private int df;

	@Override
	public int compareTo(Keyword o) {
		return Integer.compare(o.getDf(), this.getDf());
	}
}
