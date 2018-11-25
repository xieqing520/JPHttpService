package com.xieqing.jphttp.common;

public enum RequestMethod {
	GET("Get"),POST("Post");
	
	private String method="";
	private RequestMethod(String method) {
		this.method=method;
	}
	public String getMethod() {
		return method;
	}
	public static RequestMethod parse(String method) {
		switch (method.toLowerCase()) {
			case "get":
				return RequestMethod.GET;
			case "post":
				return RequestMethod.POST;
			default:
				return RequestMethod.GET;
		}
	}
}
