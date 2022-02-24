package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.BatchManagementVO;

public interface BatchManagementDAO {
	int selectManagementBatchCount(BatchManagementVO vo);
	
	List<BatchManagementVO> selectManagementBatchList(BatchManagementVO vo);
	
	int insertBatch(BatchManagementVO vo);
	
	int updateBatch(BatchManagementVO vo);
	
	int deleteBatch(BatchManagementVO vo);
}
