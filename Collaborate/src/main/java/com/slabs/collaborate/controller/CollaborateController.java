package com.slabs.collaborate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.slabs.collaborate.core.entity.User;
import com.slabs.collaborate.core.service.ServiceFactory;
import com.slabs.collaborate.utilities.JSONUtil;
import com.slabs.collaborate.core.service.CollaborateService;

@Controller
@RequestMapping("/request")
public class CollaborateController {

	private static final Logger L = LoggerFactory.getLogger(CollaborateController.class);

	@RequestMapping(value = "process/ccontroller/{action}", method = { RequestMethod.POST })
	public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String action) {

		Map<String, Object> outputMap;
		try {
			CollaborateService service = (CollaborateService) ServiceFactory.getBeanContext().getBean(action);
			Map<String, String> inputParams = JSONUtil.getObjectFromInputStream(request.getInputStream());
			outputMap = service.process(inputParams);
		} catch (IOException ex) {
			L.error("Exception: {}", ex);
			outputMap = new HashMap<String, Object>();
			outputMap.put("status", ex.getLocalizedMessage());
		} catch (Exception ex) {
			L.error("Exception: {}", ex);
			outputMap = new HashMap<String, Object>();
			outputMap.put("status", ex.getLocalizedMessage());
		}

		return new ModelAndView("jsonView", "REG_RESP", outputMap);
	}

	@RequestMapping(value = "process/retrieveUserInfo")
	public ModelAndView retrieveUserInformation(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				User user = (User) session.getAttribute("user_info");
				outputMap.put("status_code", "00");
				outputMap.put("status_msg", "Success");
				outputMap.put("success", true);
				outputMap.put("userInfo", user);
			} catch (Exception ex) {
				L.error("Exception: {}", ex);
				outputMap.put("status_code", "99");
				outputMap.put("status_msg", "Unable to retrieve User Information. Please contact customer support.");
				outputMap.put("success", false);
			}
		} else {
			L.error("Exception: Invalid Session");
			outputMap.put("status_code", "99");
			outputMap.put("status_msg", "Unable to retrieve User Information. Please contact customer support.");
			outputMap.put("success", false);
		}
		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "process/login")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap;
		try {
			CollaborateService service = (CollaborateService) ServiceFactory.getBeanContext().getBean("RetrieveUserService");
			String line = null;
			Map<String, String> inputParams;

			inputParams = new HashMap<String, String>();

			Map<String, String[]> map = request.getParameterMap();

			Set<Entry<String, String[]>> set = map.entrySet();

			for (Entry<String, String[]> e : set) {
				inputParams.put(e.getKey(), e.getValue()[0]);
			}

			outputMap = service.process(inputParams);

			if ("00".equals(outputMap.get("status_code"))) {
				User user = (User) outputMap.get("user");
				if (user.getPassword().equals(inputParams.get("password"))) {
					HttpSession session = request.getSession();
					user.setPassword("");
					session.setAttribute("user_info", user);
					session.setMaxInactiveInterval(1000);
					outputMap.put("success", true);
					outputMap.put("dashboardUrl", "/Collaborate/CollaborateDashBoard.jsp");
				} else {
					outputMap.put("status_code", "99");
					outputMap.put("status_msg", "Username/Password is incorrect");
					outputMap.remove("user");
				}
			} else if ("01".equals(outputMap.get("status_code"))) {
				outputMap.put("status_code", "99");
				outputMap.put("status_msg", "Invalid Username");
				outputMap.put("success", false);
				outputMap.remove("user");
			}
		} catch (Exception ex) {
			L.error("Exception: {}", ex);
			outputMap = new HashMap<String, Object>();
			outputMap.put("status_code", "99");
			outputMap.put("status_msg", "Exception occured. Please contact customer support");
		}
		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "process/logout")
	public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute("user_info");
				session.invalidate();
				outputMap.put("status_code", "00");
				outputMap.put("status_msg", "Logged out successfully");
				outputMap.put("loginUrl", "/Collaborate");
			} else {
				outputMap.put("status_code", "00");
				outputMap.put("status_msg", "Logged out successfully");
				outputMap.put("loginUrl", "/Collaborate");
			}
		} catch (Exception ex) {
			L.error("Exception: {}", ex);
			outputMap.put("status_code", "99");
			outputMap.put("status_msg", "Exception occured. Please contact customer support");
		}
		return new ModelAndView("jsonView", outputMap);
	}
}
