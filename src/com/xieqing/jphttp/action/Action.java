package com.xieqing.jphttp.action;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.InitialContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xieqing.jphttp.Parser;
import com.xieqing.jphttp.utils.Log;
import com.xieqing.jphttp.utils.NetUtils;
import com.xieqing.jphttp.utils.ResuorceUtil;

public abstract class Action extends ActionHandler{
	private Parser parser;
	
	public Action() {
		super();
	}
	public abstract void doAction();
	
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
		init();
	}
	
	public String __WWWROOT__="";
	public String __FILE__="";
	public String __HOST__="";
	public List<Object> defaultPage=new ArrayList<>();
	public Map<String,String> mime=new HashMap<>();
	
	
	private void init() {
		__HOST__=getParser().getHttp().getRequestHeaders().get("Host").get(0);
		readHost();
		__FILE__=__WWWROOT__+getParser().getHttpURI().getPath();
	}
	
	/*
	 * 读取网站配置
	 * 
	 * */
	private void readHost() {
		try {
			String json=ResuorceUtil.read("website.json");
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
							__WWWROOT__=jsonObject.getString("ROOT_PATH");
							defaultPage=jsonObject.getJSONArray("Defualt_File").toList();
							if (jsonObject.has("MIME")) {
								Set<String> sets=jsonObject.getJSONObject("MIME").keySet();
								for (String key:sets) {
									mime.put(key, jsonObject.getJSONObject("MIME").getString(key));
								}
							}else {
								Set<String> sets=new JSONObject(json).getJSONObject("default").getJSONObject("MIME").keySet();
								for (String key:sets) {
									mime.put(key, jsonObject.getJSONObject("MIME").getString(key));
								}
							}
							return;
						}
					}
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 文件是否存在
	 * */
	public boolean file_exists(String name) {
		return new File(name).exists();
	}
	
	/*
	 * 目录是否存在
	 * */
	public boolean is_dir(String name) {
		File file=new File(name);
		return file.exists()?file.isDirectory():false;
	}
	
	/*
	 * 是否为空
	 * */
	public boolean empty(String value) {
		return value==null?true:value.trim().length()==0;
	}
	public boolean empty(boolean value) {
		return !value;
	}
	
	/*
	 * 发送请求头
	 * */
	public void header(String header) {
		String[] split=header.split("\n");
		for (String item:split) {
			if (!empty(item)) {
				String[] e2=item.split(":");
				if (e2.length>=2) {
					getParser().getHttp().getResponseHeaders().set(e2[0], e2[1]);
				}
			}
		}
	}
	/*
	 * 取文件后缀名
	 * */
	public String getFileCovert(String path) {
		String[] split=path.split("\\.");
		if (split.length>1) {
			return split[split.length-1].trim();
		}
		return "";
	}
	
	/*
	 * 获取文件mime类型
	 * */
	public String mime_content_type(String path) {
		if (mime.containsKey(getFileCovert(path))) {
			return mime.get(getFileCovert(path));
		}
		return "application/octet-stream";
	}
	
	/*
	 * 写出字节集
	 * */
	public void write(byte[] bytes) {
		try {
			getParser().getHttp().sendResponseHeaders(200, 0);
			getParser().getHttp().getResponseBody().write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 输出
	 * */
	public void echo (String text) {
		write(text.getBytes());
	}
	
	/*
	 * 关闭连接
	 * */
	public void close () {
		try {
			getParser().getHttp().close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/*
	 * 退出并输出一句话
	 * */
	public void exit(Object object) {
		write(object.toString().getBytes());
		close();
	}
	public void die(Object object) {
		write(object.toString().getBytes());
		close();
	}
	
	/*
	 * 获取Get参数
	 * */
	public String $_Get(String name) {
		if (getParser().getHttpURI().getQuery().containsKey(name)) {
			return getParser().getHttpURI().getQuery().get(name);
		}
		return "";
	}
}
