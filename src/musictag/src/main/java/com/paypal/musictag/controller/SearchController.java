package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.SearchService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchServiceImpl;

	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	public String searchpage(ModelMap model) {
		return "/WEB-INF/pages/search.jsp";
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchAll(@RequestParam("key") String key) throws IOException {
		return MusicTagUtil.wrapResult(searchServiceImpl.searchAll(key));
	}

	@RequestMapping(value = "/artist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchArtist(@RequestParam("key") String key, @RequestParam("currPage") int currPage,
			@RequestParam("perPage") int perPage) throws IOException {
		return MusicTagUtil.wrapResult(searchServiceImpl.searchArtist(key, currPage, perPage));
	}

	@RequestMapping(value = "/release", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchRelease(@RequestParam("key") String key, @RequestParam("currPage") int currPage,
			@RequestParam("perPage") int perPage) throws IOException {
		return MusicTagUtil.wrapResult(searchServiceImpl.searchRelease(key, currPage, perPage));
	}

	@RequestMapping(value = "/recording", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchRecording(@RequestParam("key") String key, @RequestParam("currPage") int currPage,
			@RequestParam("perPage") int perPage) throws IOException {
		return MusicTagUtil.wrapResult(searchServiceImpl.searchRecording(key, currPage, perPage));
	}
}
