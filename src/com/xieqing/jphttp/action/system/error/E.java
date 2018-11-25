package com.xieqing.jphttp.action.system.error;

import com.xieqing.jphttp.action.Action;

public class E extends Action{
	public E() {
		
	}
	
	@Override
	public void doAction() {
		sendResponseCode(0);
		close();
	}

}
