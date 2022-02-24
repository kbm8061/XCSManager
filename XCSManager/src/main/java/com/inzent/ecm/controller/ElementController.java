
package com.inzent.ecm.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.ElementService;
import com.inzent.ecm.util.CommonUtils;
import com.inzent.ecm.util.ExcelUtils;
import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PropertiesVO;

@RestController
public class ElementController {
	private static final Logger logger = LoggerFactory.getLogger(ElementController.class);
	
	@Autowired
	private PropertiesVO propertiesVO;

	@Resource(name = "elementService")
	private ElementService elementService;

	@RequestMapping(value = "/getElementList")
	public ModelAndView getElementList(HttpServletRequest request, @RequestBody ElementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = elementService.selectElementCount(vo);

			if (listCount > 0) {
				List<ElementVO> elementList = elementService.selectElementList(vo);

				mav.addObject("elementCount", listCount);
				mav.addObject("elementList", elementList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<ElementVO> elementList = elementService.selectElementList(vo);

			mav.addObject("elementList", elementList);
		}

		return mav;
	}
	
	@RequestMapping(value = "getFunctionElementList")
	public ModelAndView getFunctionElementList(HttpServletRequest request, @RequestBody ElementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		int listCount = 0;
		if (vo.getStartNo() == 1) {
			listCount = elementService.selectFunctionElementCount(vo);

			if (listCount > 0) {
				List<ElementVO> elementList = elementService.selectFunctionElementList(vo);

				mav.addObject("elementCount", listCount);
				mav.addObject("elementList", elementList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<ElementVO> elementList = elementService.selectFunctionElementList(vo);

			mav.addObject("elementList", elementList);
		}

		return mav;
	}
	
	@RequestMapping(value = "getElementListByExcel")
	public ModelAndView getElementListByExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody ElementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		response.setContentType("application/octet-stream");
		
		try {
			OutputStream out = response.getOutputStream();
			
			List<ElementVO> elementList = elementService.selectExcelElementList(vo);
			
			ExcelUtils excelUtils = new ExcelUtils();
			
			SXSSFWorkbook workbook = excelUtils.getWorkbook(elementList);
			
			CommonUtils commonUtils = new CommonUtils();
			
			String tempExcelFilePath = propertiesVO.getCommonTempPath() + commonUtils.getRandomString() + ".xlsx";
			
			FileOutputStream fos = new FileOutputStream(tempExcelFilePath);

			workbook.write(fos);
			
			FileInputStream fis = new FileInputStream(tempExcelFilePath);
			
			FileCopyUtils.copy(fis, out);
			
			fis.close();
			fos.flush();
			fos.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			response.setContentType("application/json");
			mav.addObject("msg", e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
}
