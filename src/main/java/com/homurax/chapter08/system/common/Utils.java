package com.homurax.chapter08.system.common;

public class Utils {

	public static String getWord(String w) {
		int pos = w.indexOf(":");
		return w.substring(0, pos);
	}
}
