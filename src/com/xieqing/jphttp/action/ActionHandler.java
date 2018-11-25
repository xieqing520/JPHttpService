package com.xieqing.jphttp.action;

import java.io.IOException;
import java.security.DigestException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xieqing.jphttp.Parser;
import com.xieqing.jphttp.action.system.FileAction;
import com.xieqing.jphttp.action.system.error.E;
import com.xieqing.jphttp.action.system.error.E_500;
import com.xieqing.jphttp.common.RequestMethod;
import com.xieqing.jphttp.utils.IOUtils;
import com.xieqing.jphttp.utils.Log;
import com.xieqing.jphttp.utils.NetUtils;
import com.xieqing.jphttp.utils.ResuorceUtil;


public class ActionHandler {
	public String __HOST__="";
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
		__HOST__=parser.getHttp().getRequestHeaders().get("Host").get(0);
		Action action=findPath(parser.getHttpURI().getPath());
		if (action==null) {
			action=new FileAction();
		}
		action.setParser(parser);
		action.doAction();
		action.close();
	}
	
	protected JSONObject websiteConfig;
	public JSONObject getWebsiteConfig() {
		return websiteConfig;
	}

	private Action findPath(String path){
		try {
			JSONObject jsonObject=getWebsite(__HOST__);
			websiteConfig=jsonObject;
			if (jsonObject==null) {
				return new E();
			}
			if (path.startsWith("/")) {
				path=path.substring(1,path.length());
			}
			JSONArray actions = jsonObject.getJSONArray("Actions");
			for (int i = 0; i < actions.length(); i++) {
				if (actions.getJSONObject(i).getString("path").equals(path)) {
					return (Action) ClassLoader.getSystemClassLoader().loadClass(actions.getJSONObject(i).getString("class")).newInstance();
				}
			}
		} catch (JSONException e) {
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
	
	protected JSONObject getWebsite(String name) {
		String json="";
		try {
			json = ResuorceUtil.read("website.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject=new JSONObject(json);
		if (__HOST__.equals(NetUtils.getLocalIP())) {
			__HOST__="localhost";
		}
		Set<String> websKey=jsonObject.keySet();
		for (String webKey:websKey) {
			if (!webKey.equals("default")) {
				JSONArray jsonArray=jsonObject.getJSONObject(webKey).getJSONArray("HOST");
				Iterator<Object> iterator= jsonArray.iterator();
				while (iterator.hasNext()) {
					if (iterator.next().toString().equals(__HOST__)) {
						jsonObject=jsonObject.getJSONObject(webKey);
						
						return jsonObject;
					}
				}
			}
			
		}
		return null;
	}
}
