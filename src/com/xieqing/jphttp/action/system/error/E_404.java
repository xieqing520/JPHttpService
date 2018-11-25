package com.xieqing.jphttp.action.system.error;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.xieqing.jphttp.action.Action;
import com.xieqing.jphttp.action.system.FileAction;
import com.xieqing.jphttp.common.ResponeCode;
import com.xieqing.jphttp.utils.IOUtils;
import com.xieqing.jphttp.utils.Log;

public class E_404 extends Action{
	
	
	public E_404(Action action) {
		super(action);
	}

	@Override
	public void doAction() {
		header("Content-Type:text/html");
		sendResponseCode(404);
		if (getWebsiteConfig().getJSONObject("Error_Page").has(ResponeCode.C_404.codeString())) {
			String path=__WWWROOT__+"/"+getWebsiteConfig().getJSONObject("Error_Page").getString(ResponeCode.C_404.codeString());
			try {
				write(IOUtils.readStream(new FileInputStream(new File(path))));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/404/404.html");
			byte[] bs = IOUtils.readStream(inputStream);
			write(bs);
		}
		close();
	}

	

	

	

}
