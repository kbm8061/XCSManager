package com.inzent.ecm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.HistoryService;
import com.inzent.ecm.service.dao.HistoryDAO;
import com.inzent.ecm.vo.ErrLogVO;
import com.inzent.ecm.vo.HistoryVO;

@Service("historyService")
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private HistoryDAO historyMapper;
	
	@Override
	public Map<String, String> selectHistoryCount(HistoryVO vo) {
		return historyMapper.selectHistoryCount(vo);
	}
	
	@Override
	public List<HistoryVO> selectHistoryList(HistoryVO vo) {
		return historyMapper.selectHistoryList(vo);
	}

	@Override
	public Map<String, String> selectHistoryErrLogCount(ErrLogVO vo) {
		return historyMapper.selectHistoryErrLogCount(vo);
	}

	@Override
	public List<ErrLogVO> selectHistoryErrLogList(ErrLogVO vo) {
		return historyMapper.selectHistoryErrLogList(vo);
	}
}
