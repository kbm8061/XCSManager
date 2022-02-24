package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.BatchHistoryVO;

public interface BatchHistoryService {
	public int selectBatchHistoryCount(BatchHistoryVO vo);
	
	public List<BatchHistoryVO> selectBatchHistoryList(BatchHistoryVO vo);
}
