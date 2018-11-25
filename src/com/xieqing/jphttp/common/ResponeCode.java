package com.xieqing.jphttp.common;

public enum ResponeCode {
	C_200(200),C_404(404);
	
	private String codeString="200";
	private int code=200;
	private ResponeCode(int code) {
		this.codeString=code+"";
		this.code=code;
	}
	public String codeString() {
		return codeString;
	}
	public int code() {
		return code;
	}
}
