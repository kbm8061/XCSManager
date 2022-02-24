package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PerformanceVO;

public interface ElementService {
	public int selectElementCount(ElementVO vo);
	
	public List<ElementVO> selectElementList(ElementVO vo);
	
	public int selectFunctionElementCount(ElementVO vo);
	
	public List<ElementVO> selectFunctionElementList(ElementVO vo);

	public List<ElementVO> selectExcelElementList(ElementVO vo);
	
	
}
