package com.inzent.ecm.service;

import com.inzent.ecm.vo.RealtimeProcessVO;

public interface RealtimeProcessService {

	int setRealtime(RealtimeProcessVO vo);

	int setStandBy(RealtimeProcessVO vo);

}
