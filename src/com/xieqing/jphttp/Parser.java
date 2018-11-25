package com.xieqing.jphttp;

import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.xieqing.jphttp.common.RequestMethod;
import com.xieqing.jphttp.parse.HttpURI;
import com.xieqing.jphttp.utils.Log;

public class Parser {
	private HttpExchange http;
	private HttpURI httpURI;
	private RequestMethod method;
	public Parser(HttpExchange arg0) {
		this.http=arg0;
	}
	
	public void parse() {
		URI uri=http.getRequestURI();
		httpURI=parseUri(uri);
		method=RequestMethod.parse(http.getRequestMethod());
	}
	
	public RequestMethod getMethod() {
		return method;
	}
	public HttpExchange getHttp() {
		return http;
	}
	public HttpURI getHttpURI() {
		return httpURI;
	}

	private HttpURI parseUri(URI uri) {
		return new HttpURI(uri);
	}
}
