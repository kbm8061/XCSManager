package com.inzent.ecm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inzent.ecm.service.UserService;
import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.UserVO;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private PropertiesVO propertiesVO;
	
	@Resource(name = "userService")
	private UserService userService;

	@RequestMapping("/actionLogin")
	public ModelAndView actionLogin(@ModelAttribute("userVO") UserVO userVO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		try {
			UserVO vo = userService.selectUser(userVO);
			if (vo != null && vo.getUserId() != null && !vo.getUserId().equals("")) {
				int timeoutInterval = Integer.parseInt(propertiesVO.getMaxinactiveinterval().trim())*60;
				request.getSession().setMaxInactiveInterval(timeoutInterval);
				request.getSession().setAttribute("XCSMUser", vo);
				model.setViewName("redirect:home");
			} else {
				request.getSession().removeAttribute("XCSMUser");
				model.addObject("message", "로그인에 실패했습니다.");
				model.setViewName("login/login");
			}
		} catch(NullPointerException e) {
			request.getSession().removeAttribute("XCSMUser");
			model.addObject("message", "로그인에 실패했습니다.");
			model.setViewName("login/login");
		} catch(Exception e) {
			request.getSession().removeAttribute("XCSMUser");
			model.addObject("message", "로그인 중 알 수 없는 오류가 발생했습니다.");
			model.setViewName("login/login");
			logger.error(e.getMessage());
		}
		return model;
	}
	
	@RequestMapping("/actionLogout")
	public String actionLogin(HttpServletRequest request, ModelMap model) {
		request.getSession().removeAttribute("XCSMUser");

		return "redirect:login";
	}
	
	@RequestMapping("/actionJoin")
	public int actionJoin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserVO vo) throws Exception {
		try {
			return userService.insertUser(vo);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.sendError(404, e.getMessage());
			return 0;
		}
	}
	
	@ResponseBody
	@RequestMapping("/getUser")
	public UserVO getUser(HttpServletRequest request, @RequestBody UserVO vo) throws Exception {
    	return userService.selectUser(vo);
	}
	
	@ResponseBody
	@RequestMapping("/getUserList")
	public List<UserVO> getUserList(HttpServletRequest request, @RequestBody UserVO vo) throws Exception {
    	return userService.selectUserList(vo);
	}

	@ResponseBody
	@RequestMapping("/setUser")
	public int setUser(HttpServletRequest request, @RequestBody UserVO vo) throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUserId(vo.getUserId());
		
		List<UserVO> voList = userService.selectUserList(userVO);
		if (voList == null || voList.isEmpty())
			return userService.insertUser(vo);
		else
			return userService.updateUser(vo);
	}

	@ResponseBody
	@RequestMapping("/deleteUser")
	public int deleteUser(HttpServletRequest request, @RequestBody UserVO vo) throws Exception {
		return userService.deleteUser(vo);
	}
	
	@ResponseBody
	@RequestMapping("/actionAdmin")
	public int actionLogin (HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			UserVO vo = new UserVO();
			
			vo.setUserId("admin");
			vo.setPassWD("admin");
			vo.setUserName("愿�由ъ��");
			
			return userService.insertUser(vo);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.sendError(404, e.getMessage());
			return 0;
		}
	}
}
