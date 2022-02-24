package com.inzent.ecm.service.dao;

import java.util.List;

import com.inzent.ecm.vo.CodeManagementVO;

public interface CodeManagementDAO {
	List<CodeManagementVO> selectCodeList(CodeManagementVO vo);
	
	int selectManagementCodeCount(CodeManagementVO vo);
	
	List<CodeManagementVO> selectManagementCodeList(CodeManagementVO vo);
	
	int insertCode(CodeManagementVO vo);
	
	int updateCode(CodeManagementVO vo);
	
	int deleteCode(CodeManagementVO vo);
}
