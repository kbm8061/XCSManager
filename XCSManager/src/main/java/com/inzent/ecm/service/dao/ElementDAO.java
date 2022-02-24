package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PerformanceVO;

public interface ElementDAO {
	int selectElementCount(ElementVO vo);
	
	List<ElementVO> selectElementList(ElementVO vo);
	
	int selectFunctionElementCount(ElementVO vo);
	
	List<ElementVO> selectFunctionElementList(ElementVO vo);

	List<ElementVO> selectExcelElementList(ElementVO vo);
}
