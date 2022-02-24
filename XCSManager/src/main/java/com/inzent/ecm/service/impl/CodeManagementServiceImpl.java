package com.inzent.ecm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.service.CodeManagementService;
import com.inzent.ecm.service.dao.CodeManagementDAO;
import com.inzent.ecm.vo.CodeManagementVO;

@Service("codeService")
public class CodeManagementServiceImpl implements CodeManagementService {
	@Autowired
	private CodeManagementDAO codeMapper;
	
	@Override
	public List<CodeManagementVO> selectCodeList(CodeManagementVO vo) {
		return codeMapper.selectCodeList(vo);
	}

	@Override
	public int selectManagementCodeCount(CodeManagementVO vo) {
		return codeMapper.selectManagementCodeCount(vo);
	}

	@Override
	public List<CodeManagementVO> selectManagementCodeList(CodeManagementVO vo) {
		return codeMapper.selectManagementCodeList(vo);
	}

	@Override
	public int insertCode(CodeManagementVO vo) {
		return codeMapper.insertCode(vo);
	}

	@Override
	public int updateCode(CodeManagementVO vo) {
		return codeMapper.updateCode(vo);
	}

	@Override
	public int deleteCode(CodeManagementVO vo) {
		return codeMapper.deleteCode(vo);
	}
}
