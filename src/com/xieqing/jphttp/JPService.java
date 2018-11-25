package com.xieqing.jphttp;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import com.xieqing.jphttp.action.ActionHandler;
import com.xieqing.jphttp.common.Charsets;
import com.xieqing.jphttp.utils.IOUtils;
import com.xieqing.jphttp.utils.Log;
import com.xieqing.jphttp.utils.NetUtils;


public class JPService {
	private static int maxConnectedCount=100;
	private static int port=8080;
	private static HttpServer httpServer;
	public static void main(String[] args) throws IOException {
		importConfig();
		HttpServerProvider provider = HttpServerProvider.provider();
		HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), maxConnectedCount);
		httpserver.createContext("/", new HttpHandler() {
			@Override
			public void handle(HttpExchange arg0) throws IOException {
				Parser parser=new Parser(arg0);
				parser.parse();
				ActionHandler.getIntance().handle(parser);;
			}}); 
		httpserver.setExecutor(null);
		httpserver.start();
		System.out.println("JPService started at "+NetUtils.getLocalIP()+":"+port);
	}

	
	
	/*读取配置文件config.json*/
	private static void importConfig() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> file= classLoader.getResources("config.json");
		if (file.hasMoreElements()) {
			String json=IOUtils.toString(file.nextElement().openStream(),Charsets.UTF_8);
			JSONObject jsonObject=new JSONObject(json);
			jsonObject=jsonObject.getJSONObject("Main");
			maxConnectedCount=jsonObject.getInt("SERVICE_REQUEST_MAX_COUNT");
			port=jsonObject.getInt("SERVICE_PORT");
		}
	}
}
