package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.PerformanceVO;

public interface PerformanceService {
	public int selectPerformanceCount(PerformanceVO vo);

	public List<PerformanceVO> selectPerformanceList(PerformanceVO vo);
	
	public List<PerformanceVO> selectPerformanceListPaging(PerformanceVO vo);
}
