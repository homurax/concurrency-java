package com.homurax.irsystem.concurrent;

import com.homurax.irsystem.common.Token;
import lombok.Data;

import java.util.List;

@Data
public class ConcurrentInvertedIndex {

	private List<Token> index;
	
}
