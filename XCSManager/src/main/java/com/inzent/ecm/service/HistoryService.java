package com.inzent.ecm.service;

import java.util.List;
import java.util.Map;

import com.inzent.ecm.vo.ErrLogVO;
import com.inzent.ecm.vo.HistoryVO;

public interface HistoryService {
	public Map<String, String> selectHistoryCount(HistoryVO vo);
	
	public List<HistoryVO> selectHistoryList(HistoryVO vo);
	
	public Map<String, String> selectHistoryErrLogCount(ErrLogVO vo);
	
	public List<ErrLogVO> selectHistoryErrLogList(ErrLogVO vo);
}
