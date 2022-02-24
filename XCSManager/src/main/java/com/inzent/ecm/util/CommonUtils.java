package com.inzent.ecm.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {
    
    public String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public String getDateDirectory(String dir) {
		StringBuffer filePath = new StringBuffer();
		filePath.append(dir.endsWith(File.separator) ? dir : (dir + File.separator));
		filePath.append(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		return filePath.toString();
	}
}
