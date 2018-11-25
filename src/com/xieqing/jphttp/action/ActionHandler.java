package com.xieqing.jphttp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.xieqing.jphttp.Parser;
import com.xieqing.jphttp.action.system.FileAction;
import com.xieqing.jphttp.common.RequestMethod;
import com.xieqing.jphttp.utils.IOUtils;
import com.xieqing.jphttp.utils.Log;
import com.xieqing.jphttp.utils.ResuorceUtil;


public class ActionHandler {
	private static ActionHandler intance;
	public static ActionHandler getIntance() {
		if (intance==null) {
			intance=new ActionHandler();
		}
		return intance;
	}
	
	public ActionHandler() {
		
	}

	public void handle(Parser parser) {
		Action action=findPath(parser.getHttpURI().getPath());
		if (action==null) {
			action=new FileAction();
		}
		action.setParser(parser);
		action.doAction();
		action.close();
	}

	private Action findPath(String path) {
		try {
			JSONObject jsonObject=new JSONObject(ResuorceUtil.read("actions.json")).getJSONObject("actions");
			if (path.startsWith("/")) {
				path=path.substring(1,path.length());
			}
			if (jsonObject.has(path)) {
				return (Action) ClassLoader.getSystemClassLoader().loadClass(jsonObject.getJSONObject(path).getString("class")).newInstance();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
