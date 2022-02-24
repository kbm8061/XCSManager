package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.RealtimeProcessService;
import com.inzent.ecm.service.dao.RealtimeProcessDAO;
import com.inzent.ecm.vo.RealtimeProcessVO;

@Service("realtimeProcessService")
public class RealtimeProcessServiceImpl implements RealtimeProcessService {

	@Autowired
	private RealtimeProcessDAO realtimeProcessMapper;
	
	@Override
	public int setRealtime(RealtimeProcessVO vo) {
		return realtimeProcessMapper.setRealtime(vo);
	}

	@Override
	public int setStandBy(RealtimeProcessVO vo) {
		return realtimeProcessMapper.setStandBy(vo);
	}
}
