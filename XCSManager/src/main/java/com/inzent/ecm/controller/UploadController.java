package com.inzent.ecm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.ElementService;
import com.inzent.ecm.service.UploadService;
import com.inzent.ecm.vo.ElementVO;
import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.RealtimeProcessVO;

@RestController
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private PropertiesVO propertiesVO;

	@Resource(name = "uploadService")
	private UploadService uploadService;
	
	@Resource(name = "elementService")
	private ElementService elementService;

	@RequestMapping(value = "/uploadExcel")
	public ModelAndView uploadExcel(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int ret = uploadService.insertExcel(request, propertiesVO);
		
		if (ret > 0) {
			int listCount = 0;
			
			ElementVO vo = new ElementVO();
			
			vo.setClassification(request.getParameter("classification"));
			vo.setStatus("00");
			vo.setStartNo(Integer.parseInt(request.getParameter("startNo")));
			vo.setEndNo(Integer.parseInt(request.getParameter("endNo")));
			vo.setSelectCount(Integer.parseInt(request.getParameter("selectCount")));
			
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
		}

		return mav;
	}

	@RequestMapping(value = "/uploadCSV")
	public ModelAndView uploadCSV(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int ret = uploadService.insertCSV(request, propertiesVO);
		
		if (ret > 0) {
			int listCount = 0;
			
			ElementVO vo = new ElementVO();
			
			vo.setClassification(request.getParameter("classification"));
			vo.setStatus("00");
			vo.setStartNo(Integer.parseInt(request.getParameter("startNo")));
			vo.setEndNo(Integer.parseInt(request.getParameter("endNo")));
			vo.setSelectCount(Integer.parseInt(request.getParameter("selectCount")));
			
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
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/selectUploadedElement")
	public ModelAndView selectUploadedElement(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		int listCount = 0;
		
		ElementVO vo = new ElementVO();
		
		vo.setClassification(request.getParameter("classification"));
		vo.setStatus("00");
		vo.setStartNo(Integer.parseInt(request.getParameter("startNo")));
		vo.setEndNo(Integer.parseInt(request.getParameter("endNo")));
		vo.setSelectCount(Integer.parseInt(request.getParameter("selectCount")));
		
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
	
	@RequestMapping(value = "/downloadExcelSample")
	public ModelAndView downloadExcelSample(HttpServletRequest request, HttpServletResponse response, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		response.setContentType("application/octet-stream");

		try {
			OutputStream out = response.getOutputStream();
			
			File file = new ClassPathResource("sample/sample.xlsx").getFile();
			FileInputStream fis = new FileInputStream(file);

			FileCopyUtils.copy(fis, out);
			
			fis.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			response.setContentType("application/json");
			mav.addObject("msg", e.getMessage());
			logger.error(e.getMessage());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/downloadCSVSample")
	public ModelAndView downloadCSVSample(HttpServletRequest request, HttpServletResponse response, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		response.setContentType("application/octet-stream");

		try {
			OutputStream out = response.getOutputStream();
			
			File file = new ClassPathResource("sample/sample.csv").getFile();
			FileInputStream fis = new FileInputStream(file);
			
			FileCopyUtils.copy(fis, out);
			
			fis.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			response.setContentType("application/json");
			mav.addObject("msg", e.getMessage());
			logger.error(e.getMessage());
		}
		
		return mav;
	}
}
