package com.xieqing.jphttp.utils;

public class Log {
	protected static final boolean debug=true;
	public static void log(String value) {
		if (debug) {
			System.out.println(value);
		}
	}
}
