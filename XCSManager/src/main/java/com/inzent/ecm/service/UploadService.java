package com.inzent.ecm.service;

import javax.servlet.http.HttpServletRequest;

import com.inzent.ecm.vo.PropertiesVO;

public interface UploadService {
	public int insertExcel(HttpServletRequest request, PropertiesVO vo) throws Exception;
	
	public int insertCSV(HttpServletRequest request, PropertiesVO vo) throws Exception;
}
