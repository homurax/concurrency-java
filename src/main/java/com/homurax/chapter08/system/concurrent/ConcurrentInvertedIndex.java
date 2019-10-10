package com.homurax.chapter08.system.concurrent;

import com.homurax.chapter08.system.common.Token;

import java.util.List;

public class ConcurrentInvertedIndex {

	private List<Token> index;

	public void setIndex(List<Token> index) {
		this.index = index;
	}

	public List<Token> getIndex() {
		return index;
	}
}
