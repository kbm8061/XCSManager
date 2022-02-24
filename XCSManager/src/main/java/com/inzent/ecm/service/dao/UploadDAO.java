package com.inzent.ecm.service.dao;

import java.util.ArrayList;

import com.inzent.ecm.vo.ElementVO;

public interface UploadDAO {
	
	int insertElementList(ArrayList<ElementVO> vo);
	
}