package com.xieqing.jphttp.action.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.xieqing.jphttp.action.Action;
import com.xieqing.jphttp.utils.IOUtils;

public class FileAction extends Action{

	@Override
	public void doAction() {
		if (is_dir(__FILE__)) {
			for (Object object:defaultPage) {
				if (file_exists(__FILE__+"/"+object.toString())) {
					outFile(__FILE__+"/"+object.toString());
					return;
				}
			}
		}
		if (file_exists(__FILE__)) {
			outFile(__FILE__);
			return;
		}
	}

	private void outFile(String path) {
		header("Content-Type:"+mime_content_type(path));
		try {
			write(IOUtils.readStream(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
