package com.inzent.ecm.service.dao;

import java.util.List;
import java.util.Map;

import com.inzent.ecm.vo.StaticVO;

public interface StaticDAO {
	List<StaticVO> selectStandByStatic();
	
	List<StaticVO> selectElementStatic(StaticVO vo);
	
	List<StaticVO> selectDayElementStatic(Map<String, String> map);
	
	List<StaticVO> selectTotalElementStatic(Map<String, String> map);
	
}