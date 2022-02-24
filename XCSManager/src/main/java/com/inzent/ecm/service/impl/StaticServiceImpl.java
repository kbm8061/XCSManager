package com.inzent.ecm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.StaticService;
import com.inzent.ecm.service.dao.StaticDAO;
import com.inzent.ecm.vo.StaticVO;

@Service("staticService")
public class StaticServiceImpl implements StaticService {
	@Autowired
	private StaticDAO StaticMapper;

	@Override
	public List<StaticVO> selectStandByStatic() {
		return StaticMapper.selectStandByStatic();
	}

	@Override
	public List<StaticVO> selectElementStatic(StaticVO vo) {
		return StaticMapper.selectElementStatic(vo);
	}
	
	@Override
	public List<StaticVO> selectDayElementStatic(Map<String, String> map) {
		return StaticMapper.selectDayElementStatic(map);
	}
	
	
	@Override
	public List<StaticVO> selectTotalElementStatic(Map<String, String> map) {
		return StaticMapper.selectTotalElementStatic(map);
	}
	
}
