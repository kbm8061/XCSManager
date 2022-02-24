package com.inzent.ecm.service;

import java.util.List;

import com.inzent.ecm.vo.CodeManagementVO;

public interface CodeManagementService {
	public List<CodeManagementVO> selectCodeList(CodeManagementVO vo);
	
	public int selectManagementCodeCount(CodeManagementVO vo);
	
	public List<CodeManagementVO> selectManagementCodeList(CodeManagementVO vo);
	
	public int insertCode(CodeManagementVO vo);
	
	public int updateCode(CodeManagementVO vo);
	
	public int deleteCode(CodeManagementVO vo);
}
