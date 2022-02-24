package com.inzent.ecm.service;

import java.util.List;
import java.util.Map;

import com.inzent.ecm.vo.StaticVO;

public interface StaticService {
	public List<StaticVO> selectStandByStatic();
	
	public List<StaticVO> selectElementStatic(StaticVO vo);
	
	public List<StaticVO> selectDayElementStatic(Map<String, String> map);
	
	public List<StaticVO> selectTotalElementStatic(Map<String, String> map);
	
}
