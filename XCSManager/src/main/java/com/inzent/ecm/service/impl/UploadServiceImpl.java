package com.inzent.ecm.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.ecm.controller.UploadController;
import com.inzent.ecm.service.UploadService;
import com.inzent.ecm.util.ExcelUtils;
import com.inzent.ecm.util.FileUtils;
import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.UserVO;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public int insertExcel(HttpServletRequest request, PropertiesVO propertiesVO) throws Exception {
		FileUtils fileUtils = new FileUtils();
		ExcelUtils excelUtils = new ExcelUtils();
		
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(request, propertiesVO);
		
		int res = 0;

		if (list.size() > 0) {
			ArrayList<ElementVO> excelList = excelUtils.readExcel(list.get(0).get("FILE_FULL_PATH").toString());
			
			File tempdir = new File(list.get(0).get("FILE_FULL_PATH").toString());
			
			fileUtils.deleteFile(tempdir);
			
			UserVO user = (UserVO) request.getSession().getAttribute("XCSMUser");
			
			for (ElementVO vo : excelList) {
				vo.setClassification(request.getParameter("classification"));
				vo.setRegUser(user.getUserId());
			}

			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

			try {
				if (excelList.size() > 1000) {
					ArrayList<ElementVO> paramList = new ArrayList<ElementVO>();

					int insertCount = 0;
					for (int j = 0; j < excelList.size(); j++) {
						
						paramList.add(excelList.get(j));

						if (insertCount == 999 || j == excelList.size() - 1) {
							sqlSession.insert("insertElementList", paramList);
							res = 1;
							paramList.clear();
							insertCount = 0;
						} else {
							insertCount++;
						}
					}
				} else {
					sqlSession.insert("insertElementList", excelList);
					res = 1;
				}

				sqlSession.commit();
			} catch (Exception e) {
				logger.error(e.getMessage());
				res = 0;
				throw new Exception(e);
			} finally {
				sqlSession.flushStatements();
				sqlSession.close();
			}
		}
		
		return res;
	}

	@Override
	public int insertCSV(HttpServletRequest request, PropertiesVO propertiesVO) throws Exception {
		FileUtils fileUtils = new FileUtils();
		
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(request, propertiesVO);
		
		int res = 0;

		if (list.size() > 0) {
			ArrayList<ElementVO> csvList = fileUtils.readCSV(list.get(0).get("FILE_FULL_PATH").toString());
			
			File tempdir = new File(list.get(0).get("FILE_FULL_PATH").toString());
			
			fileUtils.deleteFile(tempdir);
			
			UserVO user = (UserVO) request.getSession().getAttribute("XCSMUser");
			
			for (ElementVO vo : csvList) {
				vo.setClassification(request.getParameter("classification"));
				vo.setRegUser(user.getUserId());
			}

			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

			try {
				if (csvList.size() > 1000) {
					ArrayList<ElementVO> paramList = new ArrayList<ElementVO>();

					int insertCount = 0;
					
					for (int j = 0; j < csvList.size(); j++) {
						paramList.add(csvList.get(j));

						if (insertCount == 999 || j == csvList.size() - 1) {
							sqlSession.insert("insertElementList", paramList);
							res = 1;
							paramList.clear();
							insertCount = 0;
						} else {
							insertCount++;
						}
					}
				} else {
					sqlSession.insert("insertElementList", csvList);
					res = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				res = 0;
			} finally {
				sqlSession.commit();
				sqlSession.flushStatements();
				sqlSession.close();
			}

		}

		return res;
	}
}
