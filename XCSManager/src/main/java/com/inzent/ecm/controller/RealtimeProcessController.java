package com.inzent.ecm.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zeroturnaround.zip.ZipUtil;

import com.inzent.ecm.service.RealtimeProcessService;
import com.inzent.ecm.util.EcmUtils;
import com.inzent.ecm.util.FileUtils;
import com.inzent.ecm.vo.PropertiesVO;
import com.inzent.ecm.vo.RealtimeProcessVO;

@RestController
public class RealtimeProcessController {
	private static final Logger logger = LoggerFactory.getLogger(RealtimeProcessController.class);
	
	@Autowired
	private PropertiesVO propertiesVO;

	@Resource(name = "realtimeProcessService")
	private RealtimeProcessService realtimeProcessService;
	
	@RequestMapping(value = "/downloadEngine")
	public ModelAndView downloadEngine(HttpServletRequest request, HttpServletResponse response, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		response.setContentType("application/octet-stream");
		
		EcmUtils ecmUtils = new EcmUtils();
		FileUtils fileUtils = new FileUtils();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			OutputStream out = response.getOutputStream();

			resultMap = ecmUtils.downloadEngine(vo, propertiesVO);

			ZipUtil.pack(new File(resultMap.get("file").toString()), out);

			out.flush();
			out.close();
		} catch (Exception e) {
			response.setContentType("application/json");
			mav.addObject("msg", e.getMessage());
			logger.error(e.getMessage());
		} finally {
			try {
				fileUtils.deleteDirectory(new File(resultMap.get("tempDir").toString()));
			} catch (Exception e) {
				response.setContentType("application/json");
				mav.addObject("msg", e.getMessage());
				logger.error(e.getMessage());
			}
		}

		return mav;
	}

	@RequestMapping(value = "/downloadDirect")
	public ModelAndView downloadDirect(HttpServletRequest request, HttpServletResponse response, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		response.setContentType("application/octet-stream");
		
		FileUtils fileUtils = new FileUtils();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			OutputStream out = response.getOutputStream();
			resultMap = fileUtils.downloadDirect(vo, propertiesVO);
			
			ZipUtil.pack(new File(resultMap.get("file").toString()), out);
			
			out.flush();
			out.close();
		} catch (Exception e) {
			response.setContentType("application/json");
			mav.addObject("msg", e.getMessage());
			logger.error(e.getMessage());
		} finally {
			try {
				fileUtils.deleteDirectory(new File(resultMap.get("tempDir").toString()));
			} catch (Exception e) {
				response.setContentType("application/json");
				mav.addObject("msg", e.getMessage());
				logger.error(e.getMessage());
			}
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/setRealtime")
	public ModelAndView setRealtime(HttpServletRequest request, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		
		int res = 0;
		
		try {
			res = realtimeProcessService.setRealtime(vo);
			
			if (res > 0) {
				mav.addObject("msg", res);
			}
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/setStandBy")
	public ModelAndView setStandBy(HttpServletRequest request, @RequestBody RealtimeProcessVO vo) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");

		int res = 0;
		
		try {
			res = realtimeProcessService.setStandBy(vo);
			
			if (res > 0) {
				mav.addObject("msg", res);
			}
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
		}
		
		return mav;
	}
}
