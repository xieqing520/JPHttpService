package com.xieqing.jphttp.parse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;

import com.xieqing.jphttp.utils.Log;

public class HttpURI {
	private URI uri;
	private Map<String, String> query=new HashMap<>();
	public HttpURI(URI uri) {
		this.uri=uri;
		init();
	}
	private void init() {
		parseQuery();
	}
	
	/*解析出请求参数*/
	private void parseQuery() {
		if (uri.getRawQuery()==null) {
			return;
		}
		String[] arrSplit=uri.getRawQuery().split("[&]");
        for(String strSplit:arrSplit){
        	String name="";
        	String value="";
        	int splitLen=strSplit.indexOf("=");
        	if (splitLen<0) {
        		name=strSplit;
			}else {
				name=strSplit.substring(0, splitLen);
				value=strSplit.substring(splitLen+1, strSplit.length());
			}
        	query.put(name,value);  
        }   
	}
	public Map<String, String> getQuery() {
		return query;
	}
	
	public String getPath() {
		return uri.getPath();
	}
}
