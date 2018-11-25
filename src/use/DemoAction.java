package use;

import com.xieqing.jphttp.action.Action;

public class DemoAction extends Action{

	@Override
	public void doAction() {
		header("Content-type: text/html; charset=utf-8"); 
		echo("你好"+$_Get("r"));
	}
	
}
