package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.PerformanceVO;

public interface PerformanceDAO {
	int selectPerformanceCount(PerformanceVO vo);

	List<PerformanceVO> selectPerformanceList(PerformanceVO vo);
	
	List<PerformanceVO> selectPerformanceListPaging(PerformanceVO vo);
}
