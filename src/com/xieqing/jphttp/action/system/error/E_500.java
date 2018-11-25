package com.xieqing.jphttp.action.system.error;

import com.xieqing.jphttp.action.Action;

public class E_500 extends E{
	int code;String message;
	public E_500() {
		this.code=0;
		this.message="";
	}
	public E_500(int code,String message) {
		this.code=code;
		this.message=message;
	}
	
	@Override
	public void doAction() {
		sendResponseCode(code);
		write(message.getBytes());
		close();
	}

}
