package com.xieqing.jphttp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class IOUtils {
	public static byte[] readStream(InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len=0;
		try {
			while ((len=inputStream.read(buffer))>0) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}
	public static String toString(InputStream inputStream,String charest) {
		try {
			return new String(readStream(inputStream),charest);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
