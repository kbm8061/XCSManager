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

import com.inzent.ecm.service.BatchHistoryService;
import com.inzent.ecm.vo.BatchHistoryVO;

@RestController
public class BatchHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(BatchHistoryController.class);
	
	@Resource(name = "batchHistoryService")
	private BatchHistoryService batchHistoryService;
	
	@RequestMapping(value = "/getBatchHistoryList")
	public ModelAndView getBatchHistoryList(HttpServletRequest request, @RequestBody BatchHistoryVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = batchHistoryService.selectBatchHistoryCount(vo);
			
			if (listCount > 0) {
				List<BatchHistoryVO> batchHistoryList = batchHistoryService.selectBatchHistoryList(vo);
				
				mav.addObject("batchHistoryCount", listCount);
				mav.addObject("batchHistoryList", batchHistoryList);
			}
			else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		}
		else {
			List<BatchHistoryVO> batchHistoryList = batchHistoryService.selectBatchHistoryList(vo);
			
			mav.addObject("batchHistoryList", batchHistoryList);
		}
		
		return mav;
	}
}
