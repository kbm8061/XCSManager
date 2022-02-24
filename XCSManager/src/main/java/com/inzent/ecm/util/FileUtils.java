package com.inzent.ecm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.RealtimeProcessVO;

public class FileUtils {

	public void makeDirectory(String dir) {
		File file = new File(dir);

		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public boolean deleteDirectory(File directory) throws IOException {
		org.apache.commons.io.FileUtils.deleteDirectory(directory);
		return directory.exists();
	}

	public Map<String, Object> downloadDirect(RealtimeProcessVO vo, PropertiesVO propertiesVO) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> msgList = new HashMap<String, Object>();

		String[] elementIds = vo.getElementId();
		String[] filePaths = vo.getFilePath();

		CommonUtils commonUtils = new CommonUtils();
		String tempPath = propertiesVO.getCommonTempPath();
		String tempDir = tempPath + commonUtils.getRandomString();
		String downloadDir = commonUtils.getDateDirectory(tempDir);

		makeDirectory(downloadDir);

		for (int i = 0; i < elementIds.length; i++) {
			FileInputStream fis = null;
			FileOutputStream fos = null;

			String orgFile = filePaths[i];
			String copyFile = downloadDir + File.separator + elementIds[i];

			fis = new FileInputStream(orgFile);
			fos = new FileOutputStream(copyFile);

			byte[] buff = new byte[1024];

			int read;

			while ((read = fis.read(buff)) != -1) {
				fos.write(buff, 0, read);
			}

			fis.close();
			fos.flush();
			fos.close();
		}

		File zipDir = new File(downloadDir);

		resultMap.put("msg", msgList);
		resultMap.put("file", zipDir);
		resultMap.put("tempDir", tempDir);

		return resultMap;
	}

	public List<Map<String, Object>> parseInsertFileInfo(HttpServletRequest request, PropertiesVO propertiesVO) throws Exception {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap = null;

		String tempPath = propertiesVO.getCommonTempPath();

		File file = new File(tempPath);

		if (file.exists() == false) {
			file.mkdirs();
		}

		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if (multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

				CommonUtils commonUtils = new CommonUtils();
				storedFileName = commonUtils.getRandomString() + originalFileExtension;

				file = new File(tempPath + storedFileName);
				multipartFile.transferTo(file);

				listMap = new HashMap<String, Object>();
				listMap.put("ORIGINAL_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				listMap.put("FILE_FULL_PATH", tempPath + storedFileName);
				list.add(listMap);
			}
		}

		return list;
	}
	
	public ArrayList<ElementVO> readCSV(String filePath) throws IOException {
		ArrayList<ElementVO> resultList = new ArrayList<ElementVO>();
		
		FileInputStream fileInputStream = new FileInputStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
		
		String line;
		
		int readCnt = 0;
		
		while((line = br.readLine()) != null) {
			if (readCnt != 0) {
				String[] temp = line.split(",");
				
				ElementVO vo = new ElementVO();
				
				for(int index = 0; index < temp.length; index++) {
					switch (index) {
					case 0:
						vo.setElementId(temp[index].trim());
						break;
						
					case 1:
						vo.setOwner(temp[index].trim());
						break;
						
					case 2:
						vo.setIndexKey(temp[index].trim());
						break;

					default:
						break;
					}
				}
				
				resultList.add(vo);
			}
			readCnt++;
		}
		
		br.close();
		
		return resultList;
	}

	public boolean deleteFile(File file) {
		if (file.exists()) {
			return file.delete();
		} else {
			return false;
		}
	}
}