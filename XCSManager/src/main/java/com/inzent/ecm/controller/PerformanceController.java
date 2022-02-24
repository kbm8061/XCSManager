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

import com.inzent.ecm.service.PerformanceService;
import com.inzent.ecm.vo.PerformanceVO;

@RestController
public class PerformanceController {
private static final Logger logger = LoggerFactory.getLogger(PerformanceController.class);
	
	@Resource(name = "performanceService")
	private PerformanceService performanceService;
	
	@RequestMapping(value = "/getPerformanceListPaging")
	public ModelAndView getPerformanceListPaging(HttpServletRequest request, @RequestBody PerformanceVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = performanceService.selectPerformanceCount(vo);
			
			if (listCount > 0) {
				List<PerformanceVO> performanceList = performanceService.selectPerformanceListPaging(vo);
				
				mav.addObject("performanceCount", listCount);
				mav.addObject("performanceList", performanceList);
			}
			else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		}
		else {
			List<PerformanceVO> performanceList = performanceService.selectPerformanceListPaging(vo);
			
			mav.addObject("performanceList", performanceList);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getPerformanceList")
	public ModelAndView getPerformaceList(HttpServletRequest request, @RequestBody PerformanceVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		List<PerformanceVO> performanceList = performanceService.selectPerformanceList(vo);
		
		mav.addObject("performanceList", performanceList);
		
		return mav;
	}
}
