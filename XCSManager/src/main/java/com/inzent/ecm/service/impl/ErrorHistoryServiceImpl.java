package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.ErrorHistoryService;
import com.inzent.ecm.service.dao.ErrorHistoryDAO;
import com.inzent.ecm.vo.ErrorHistoryVO;

@Service("errorHistoryService")
public class ErrorHistoryServiceImpl implements ErrorHistoryService {
	@Autowired
	private ErrorHistoryDAO errorHistoryMapper;

	@Override
	public int selectErrorHistoryCount(ErrorHistoryVO vo) {
		return errorHistoryMapper.selectErrorHistoryCount(vo);
	}

	@Override
	public List<ErrorHistoryVO> selectErrorHistoryList(ErrorHistoryVO vo) {
		return errorHistoryMapper.selectErrorHistoryList(vo);
	}
}
