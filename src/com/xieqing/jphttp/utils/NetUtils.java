package com.xieqing.jphttp.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {
	public static String getLocalIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "127.0.0.1";
	}
}
