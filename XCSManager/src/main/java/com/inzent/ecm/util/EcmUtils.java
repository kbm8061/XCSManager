package com.inzent.ecm.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.ZipUtil;

import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.RealtimeProcessVO;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.asys.asysUsrElement;

public class EcmUtils {

	public Map<String, Object> downloadEngine(RealtimeProcessVO vo, PropertiesVO propertiesVO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> msgList = new HashMap<String, Object>();
		
		asysConnectData con = new asysConnectData(propertiesVO.getEngineIp(), Integer.parseInt(propertiesVO.getEnginePort()), propertiesVO.getDescription(), propertiesVO.getEngineId(), propertiesVO.getEnginePw());
		
		asysUsrElement usr = new asysUsrElement(con);
		
		CommonUtils commonUtils = new CommonUtils();
		String tempPath = propertiesVO.getCommonTempPath();
		String tempDir = tempPath + commonUtils.getRandomString();
		String downloadDir = commonUtils.getDateDirectory(tempDir);
		
		FileUtils fileUtils = new FileUtils();
		
		fileUtils.makeDirectory(downloadDir);
		
		for (String elementId : vo.getElementId()) {
			usr.m_elementId = propertiesVO.getGateway() + "::" + elementId + "::" + propertiesVO.getEclassId();
			
			int ret = usr.getContent(downloadDir + File.separator + elementId);
			
			if (ret != 0) {
				msgList.put(elementId, "Error, download normal, " + usr.getLastError());
			} else {
				msgList.put(elementId, "Success, download normal, " + usr.m_elementId);
			}
		}
		
		File zipDir = new File(downloadDir);
		
		resultMap.put("msg", msgList);
		resultMap.put("file", zipDir);
		resultMap.put("tempDir", tempDir);
		
		if (con != null) {
			con.close();
			con = null;
		}
		
		return resultMap;
	}
}
