package com.inzent.ecm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.RecoveryRegistService;
import com.inzent.ecm.vo.RecoveryRegistVO;

@RestController
public class RecoveryRegistController {
	private static final Logger logger = LoggerFactory.getLogger(RecoveryRegistController.class);

	@Resource(name = "recoveryRegistService")
	private RecoveryRegistService recoveryRegistService;
	
	@RequestMapping(value = "/recoveryElements")
	public ModelAndView recoveryElements(HttpServletRequest request, @RequestBody RecoveryRegistVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		if (vo.getElementIdList() != null) {
			res = recoveryRegistService.recoveryRegist(vo);
			
			if (res > 0) {
				mav.addObject("res", res);
			}
			else {
				mav.addObject("msg", "대기상태로 변경 중 오류가 발생하였습니다.");
			}
		}
		else {
			mav.addObject("msg", "대기상태로 변경 중 오류가 발생하였습니다.");
		}
		return mav;
	}
}
