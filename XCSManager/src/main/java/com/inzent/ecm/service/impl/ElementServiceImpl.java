package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.ElementService;
import com.inzent.ecm.service.dao.ElementDAO;
import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PerformanceVO;

@Service("elementService")
public class ElementServiceImpl implements ElementService {
	@Autowired
	private ElementDAO elementMapper;

	@Override
	public int selectElementCount(ElementVO vo) {
		return elementMapper.selectElementCount(vo);
	}

	@Override
	public List<ElementVO> selectElementList(ElementVO vo) {
		return elementMapper.selectElementList(vo);
	}
	
	@Override
	public int selectFunctionElementCount(ElementVO vo) {
		return elementMapper.selectFunctionElementCount(vo);
	}

	@Override
	public List<ElementVO> selectFunctionElementList(ElementVO vo) {
		return elementMapper.selectFunctionElementList(vo);
	}

	@Override
	public List<ElementVO> selectExcelElementList(ElementVO vo) {
		return elementMapper.selectExcelElementList(vo);
	}
}
