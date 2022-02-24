package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.BatchHistoryVO;

public interface BatchHistoryDAO {
	int selectBatchHistoryCount(BatchHistoryVO vo);
	
	List<BatchHistoryVO> selectBatchHistoryList(BatchHistoryVO vo);
}
