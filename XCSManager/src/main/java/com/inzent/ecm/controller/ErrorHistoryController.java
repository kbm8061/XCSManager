package com.inzent.ecm.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.ErrorHistoryService;
import com.inzent.ecm.vo.ErrorHistoryVO;

@RestController
public class ErrorHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorHistoryController.class);
	
	@Resource(name = "errorHistoryService")
	private ErrorHistoryService errorHistoryService;
	
	@RequestMapping(value = "/getErrorHistoryList")
	public ModelAndView getErrorHistoryList(HttpServletRequest request, @RequestBody ErrorHistoryVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = errorHistoryService.selectErrorHistoryCount(vo);
			
			if (listCount > 0) {
				List<ErrorHistoryVO> errorHistoryList = errorHistoryService.selectErrorHistoryList(vo);
				
				mav.addObject("errorHistoryCount", listCount);
				mav.addObject("errorHistoryList", errorHistoryList);
			}
			else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		}
		else {
			List<ErrorHistoryVO> errorHistoryList = errorHistoryService.selectErrorHistoryList(vo);
			
			mav.addObject("errorHistoryList", errorHistoryList);
		}
		
		return mav;
	}
}
