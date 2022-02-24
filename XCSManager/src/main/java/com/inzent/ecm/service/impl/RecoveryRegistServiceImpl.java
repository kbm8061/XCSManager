package com.inzent.ecm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.RecoveryRegistService;
import com.inzent.ecm.service.dao.RecoveryRegistDAO;
import com.inzent.ecm.vo.RecoveryRegistVO;

@Service("recoveryRegistService")
public class RecoveryRegistServiceImpl implements RecoveryRegistService {

	@Autowired
	private RecoveryRegistDAO recoveryRegistMapper;
	
	@Override
	public int recoveryRegist(RecoveryRegistVO vo) {
		return recoveryRegistMapper.recoveryRegist(vo);
	}

}
