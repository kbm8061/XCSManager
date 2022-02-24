package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.BatchHistoryService;
import com.inzent.ecm.service.dao.BatchHistoryDAO;
import com.inzent.ecm.vo.BatchHistoryVO;

@Service("batchHistoryService")
public class BatchHistoryServiceImpl implements BatchHistoryService {
	@Autowired
	private BatchHistoryDAO batchHistoryMapper;

	@Override
	public int selectBatchHistoryCount(BatchHistoryVO vo) {
		return batchHistoryMapper.selectBatchHistoryCount(vo);
	}

	@Override
	public List<BatchHistoryVO> selectBatchHistoryList(BatchHistoryVO vo) {
		return batchHistoryMapper.selectBatchHistoryList(vo);
	}
}
