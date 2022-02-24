package com.inzent.ecm.service.dao;

import java.util.List;
import java.util.Map;

import com.inzent.ecm.vo.ErrLogVO;
import com.inzent.ecm.vo.HistoryVO;

public interface HistoryDAO {
	Map<String, String> selectHistoryCount(HistoryVO vo);
	
	List<HistoryVO> selectHistoryList(HistoryVO vo);
	
	Map<String, String> selectHistoryErrLogCount(ErrLogVO vo);
	
	List<ErrLogVO> selectHistoryErrLogList(ErrLogVO vo);
}
