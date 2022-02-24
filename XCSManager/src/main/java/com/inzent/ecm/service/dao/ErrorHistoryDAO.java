package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.ErrorHistoryVO;

public interface ErrorHistoryDAO {
	int selectErrorHistoryCount(ErrorHistoryVO vo);
	
	List<ErrorHistoryVO> selectErrorHistoryList(ErrorHistoryVO vo);
}
