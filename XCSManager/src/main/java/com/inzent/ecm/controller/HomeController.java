package com.inzent.ecm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/login")
	public String login() {
		logger.debug("Call Login Page");
		return "login/login";
	}
	
	@RequestMapping(value = {"/", "home"})
	public String home() {
		logger.debug("Call Home Page");
		return "tiles/main/statistics/dashboard";
	}
	
	@RequestMapping(value = {"/statistics/dashBoard"})
	public String dashBoard() {
		logger.debug("Call Dashboard Statistics Page");
		return "tiles/main/statistics/dashboard";
	}
	
	@RequestMapping(value = {"/statistics/static"})
	public String stat() {
		logger.debug("Call Static Statistics Page");
		return "tiles/main/statistics/static";
	}
	
	@RequestMapping(value = "/search/element")
	public String elementSearch() {
		logger.debug("Call Element Search Page");
		return "tiles/main/search/element";
	}
	
	@RequestMapping(value = "/function/realtimeProcess")
	public String realtimeProcess() {
		logger.debug("Call Realtime Process Page");
		return "tiles/main/function/realtimeProcess";
	}
	
	@RequestMapping(value = "/function/recoveryRegist")
	public String rvReg() {
		logger.debug("Call Recovery Regist Page");
		return "tiles/main/function/recoveryRegist";
	}
	
	@RequestMapping(value = "/search/performance")
	public String performanceSearch() {
		logger.debug("Call Performance Search Page");
		return "tiles/main/search/performance";
	}
	
	@RequestMapping(value = "/search/batchHistory")
	public String batchSearch() {
		logger.debug("Call Batch Search Page");
		return "tiles/main/search/batchHistory";
	}
	
	@RequestMapping(value = "/search/errorHistory")
	public String errorSearch() {
		logger.debug("Call Error Search Page");
		return "tiles/main/search/errorHistory";
	}
	
	@RequestMapping(value = {"/upload/excel"})
	public String excelUpload() {
		logger.debug("Call Excel Upload Page");
		return "tiles/main/upload/excel";
	}
	
	@RequestMapping(value = {"/upload/csv"})
	public String csvUpload() {
		logger.debug("Call CSV Upload Page");
		return "tiles/main/upload/csv";
	}
	
	@RequestMapping(value = "/management/code")
	public String codeManagement() {
		logger.debug("Call Code Management Page");
		return "tiles/main/management/code";
	}
	
	@RequestMapping(value = "/management/batch")
	public String batchManagement() {
		logger.debug("Call Batch Management Page");
		return "tiles/main/management/batch";
	}
	
	@RequestMapping(value = "/management/user")
	public String userManagement() {
		logger.debug("Call User Management Page");
		return "tiles/main/management/user";
	}
}
