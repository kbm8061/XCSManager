package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.BatchManagementVO;

public interface BatchManagementService {
	public int selectManagementBatchCount(BatchManagementVO vo);
	
	public List<BatchManagementVO> selectManagementBatchList(BatchManagementVO vo);
	
	public int insertBatch(BatchManagementVO vo);
	
	public int updateBatch(BatchManagementVO vo);
	
	public int deleteBatch(BatchManagementVO vo);
}
