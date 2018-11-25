package com.xieqing.jphttp.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.xieqing.jphttp.common.Charsets;

public class ResuorceUtil {
	/*读取资源文件*/
	public static String read(String name) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> file= classLoader.getResources(name);
		if (file.hasMoreElements()) {
			String txt=IOUtils.toString(file.nextElement().openStream(),Charsets.UTF_8);
			return txt;
		}
		return"";
	}
}
