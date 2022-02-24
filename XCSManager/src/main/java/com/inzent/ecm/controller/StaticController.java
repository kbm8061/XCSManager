package com.inzent.ecm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.StaticService;
import com.inzent.ecm.vo.StaticVO;

@RestController
public class StaticController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Resource(name = "staticService")
	private StaticService staticService;
	
	@RequestMapping(value = "/getElementStat")
	public ModelAndView getElementStat(HttpServletRequest request, @RequestBody Map<String, String> map) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		StaticVO paramVO = new StaticVO();
		
		//today
		Date nowDate = new Date();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd"); //원하는 데이터 포맷 지정 
		String strNowDate = simpleDateFormat.format(nowDate); //지정한 포맷으로 변환 

		paramVO.setStartDay(strNowDate + "000000");
		paramVO.setEndDay(strNowDate + "235959");
		
		// classification 설정
		paramVO.setClassification(map.get("classification"));
		
		List<StaticVO> todayStaticList = staticService.selectElementStatic(paramVO);
		if (todayStaticList.size() > 0) {
			mav.addObject("todayStaticList", todayStaticList);
			
		}
		else {
			mav.addObject("msg", "조회 된 목록이 없습니다.");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/getStandByStat")
	public ModelAndView getStandByStat(HttpServletRequest request, @RequestBody Map<String, String> map) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		//Static
		List<StaticVO> totalStaticList = staticService.selectTotalElementStatic(map);
		if (totalStaticList.size() > 0) {
			mav.addObject("totalStaticList", totalStaticList);
		}
		else {
			mav.addObject("msg", "조회 된 목록이 없습니다.");
		}

		List<StaticVO> dayStaticList = staticService.selectDayElementStatic(map);
		if (dayStaticList.size() > 0) {
			mav.addObject("dayStaticList", dayStaticList);
		}
		else {
			mav.addObject("msg", "조회 된 목록이 없습니다.");
		}
		
		return mav;
	}
}
