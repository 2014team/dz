package com.artcweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artcweb.baen.AdminUser;
import com.artcweb.util.SessionUtil;

@Controller
@RequestMapping("/admin/center/")
public class WelcomeController {

	
	@RequestMapping(value = "/welcome")
	public String toList(HttpServletRequest request) {
		AdminUser user = SessionUtil.getSessionUser(request);
		if (null == user) {
			return "redirect:/admin/login.do";
		}
		request.setAttribute("user", user);
		return "/welcome";
	}
}
