package com.inzent.ecm.service.dao;

import com.inzent.ecm.vo.RealtimeProcessVO;

public interface RealtimeProcessDAO {

	int setRealtime(RealtimeProcessVO vo);

	int setStandBy(RealtimeProcessVO vo);

}
