package com.paypal.musictag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	public String searchpage(ModelMap model) {
		return "/WEB-INF/pages/search.jsp";
	}
}
