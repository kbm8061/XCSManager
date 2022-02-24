package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.BatchManagementService;
import com.inzent.ecm.service.dao.BatchManagementDAO;
import com.inzent.ecm.vo.BatchManagementVO;

@Service("batchService")
public class BatchManagementServiceImpl implements BatchManagementService {
	@Autowired
	private BatchManagementDAO batchMapper;

	@Override
	public int selectManagementBatchCount(BatchManagementVO vo) {
		return batchMapper.selectManagementBatchCount(vo);
	}

	@Override
	public List<BatchManagementVO> selectManagementBatchList(BatchManagementVO vo) {
		return batchMapper.selectManagementBatchList(vo);
	}

	@Override
	public int insertBatch(BatchManagementVO vo) {
		return batchMapper.insertBatch(vo);
	}

	@Override
	public int updateBatch(BatchManagementVO vo) {
		return batchMapper.updateBatch(vo);
	}

	@Override
	public int deleteBatch(BatchManagementVO vo) {
		return batchMapper.deleteBatch(vo);
	}

}
