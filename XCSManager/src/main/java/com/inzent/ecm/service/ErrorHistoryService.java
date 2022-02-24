package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.ErrorHistoryVO;

public interface ErrorHistoryService {
	public int selectErrorHistoryCount(ErrorHistoryVO vo);
	
	public List<ErrorHistoryVO> selectErrorHistoryList(ErrorHistoryVO vo);
}
