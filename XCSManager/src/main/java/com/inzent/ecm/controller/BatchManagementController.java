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

import com.inzent.ecm.service.BatchManagementService;
import com.inzent.ecm.vo.BatchManagementVO;

@RestController
public class BatchManagementController {
	private static final Logger logger = LoggerFactory.getLogger(CodeManagementController.class);
	 
	@Resource(name = "batchService")
	private BatchManagementService batchService;
	
	@RequestMapping(value = "/getManagementBatchList")
	public ModelAndView getManagementBatchList(HttpServletRequest request, @RequestBody BatchManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = batchService.selectManagementBatchCount(vo);
			
			if (listCount > 0) {
				List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
				
				mav.addObject("batchCount", listCount);
				mav.addObject("batchList", batchList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
			
			mav.addObject("batchList", batchList);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/insertBatch")
	public ModelAndView insertBatch(HttpServletRequest request, @RequestBody BatchManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = batchService.insertBatch(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());			
		}
		
		if (res > 0) {
			int listCount = 0;
			if (vo.getStartNo() == 1) {
				listCount = batchService.selectManagementBatchCount(vo);
				
				if (listCount > 0) {
					List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
					
					mav.addObject("batchCount", listCount);
					mav.addObject("batchList", batchList);
				} else {
					mav.addObject("msg", "조회 된 목록이 없습니다.");
				}
			} else {
				List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
				
				mav.addObject("batchList", batchList);
			}
		} else {
			mav.addObject("res", res);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/updateBatch")
	public ModelAndView updateBatch(HttpServletRequest request, @RequestBody BatchManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = batchService.updateBatch(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		int listCount = 0;
		if (vo.getStartNo() == 1) {
			listCount = batchService.selectManagementBatchCount(vo);
			
			if (listCount > 0) {
				List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
				
				mav.addObject("batchCount", listCount);
				mav.addObject("batchList", batchList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
			
			mav.addObject("batchList", batchList);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/deleteBatch")
	public ModelAndView deleteBatch(HttpServletRequest request, @RequestBody BatchManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = batchService.deleteBatch(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		int listCount = 0;
		if (vo.getStartNo() == 1) {
			listCount = batchService.selectManagementBatchCount(vo);
			
			if (listCount > 0) {
				List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
				
				mav.addObject("batchCount", listCount);
				mav.addObject("batchList", batchList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<BatchManagementVO> batchList = batchService.selectManagementBatchList(vo);
			
			mav.addObject("batchList", batchList);
		}
		
		return mav;
	}
}
