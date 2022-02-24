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

import com.inzent.ecm.service.CodeManagementService;
import com.inzent.ecm.vo.CodeManagementVO;

@RestController
public class CodeManagementController {
	private static final Logger logger = LoggerFactory.getLogger(CodeManagementController.class);
	 
	@Resource(name = "codeService")
	private CodeManagementService codeService;
	
	@RequestMapping(value = "/getCodeList")
	public List<CodeManagementVO> getCodeList(HttpServletRequest request, @RequestBody CodeManagementVO vo) {
		if (vo.getCodeGroup().isEmpty()) { return null; }
		return codeService.selectCodeList(vo);
	}
	
	@RequestMapping(value = "/getManagementCodeList")
	public ModelAndView getManagementCodeList(HttpServletRequest request, @RequestBody CodeManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int listCount = 0;
		if (vo.getStartNo() == 1 && vo.getSelectCount() == 0) {
			listCount = codeService.selectManagementCodeCount(vo);
			
			if (listCount > 0) {
				List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
				
				mav.addObject("codeCount", listCount);
				mav.addObject("codeList", codeList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
			
			mav.addObject("codeList", codeList);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/insertCode")
	public ModelAndView insertCode(HttpServletRequest request, @RequestBody CodeManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = codeService.insertCode(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());			
		}
		
		if (res > 0) {
			int listCount = 0;
			if (vo.getStartNo() == 1) {
				listCount = codeService.selectManagementCodeCount(vo);
				
				if (listCount > 0) {
					List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
					
					mav.addObject("codeCount", listCount);
					mav.addObject("codeList", codeList);
				} else {
					mav.addObject("msg", "조회 된 목록이 없습니다.");
				}
			} else {
				List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
				
				mav.addObject("codeList", codeList);
			}
		} else {
			mav.addObject("res", res);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/updateCode")
	public ModelAndView updateCode(HttpServletRequest request, @RequestBody CodeManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = codeService.updateCode(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		int listCount = 0;
		if (vo.getStartNo() == 1) {
			listCount = codeService.selectManagementCodeCount(vo);
			
			if (listCount > 0) {
				List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
				
				mav.addObject("codeCount", listCount);
				mav.addObject("codeList", codeList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
			
			mav.addObject("codeList", codeList);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/deleteCode")
	public ModelAndView deleteCode(HttpServletRequest request, @RequestBody CodeManagementVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		try {
			res = codeService.deleteCode(vo);
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		int listCount = 0;
		if (vo.getStartNo() == 1) {
			listCount = codeService.selectManagementCodeCount(vo);
			
			if (listCount > 0) {
				List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
				
				mav.addObject("codeCount", listCount);
				mav.addObject("codeList", codeList);
			} else {
				mav.addObject("msg", "조회 된 목록이 없습니다.");
			}
		} else {
			List<CodeManagementVO> codeList = codeService.selectManagementCodeList(vo);
			
			mav.addObject("codeList", codeList);
		}
		
		return mav;
	}
}
 