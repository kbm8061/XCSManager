package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.PerformanceService;
import com.inzent.ecm.service.dao.PerformanceDAO;
import com.inzent.ecm.vo.PerformanceVO;

@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService {
	
	@Autowired
	private PerformanceDAO performanceMapper;
	
	@Override
	public int selectPerformanceCount(PerformanceVO vo) {
		return performanceMapper.selectPerformanceCount(vo);
	}

	@Override
	public List<PerformanceVO> selectPerformanceList(PerformanceVO vo) {
		return performanceMapper.selectPerformanceList(vo);
	}
	
	@Override
	public List<PerformanceVO> selectPerformanceListPaging(PerformanceVO vo) {
		return performanceMapper.selectPerformanceListPaging(vo);
	}
}
