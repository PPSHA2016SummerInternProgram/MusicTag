package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.SearchService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

	@Autowired
	private SearchService searchServiceImpl;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String searchPage(@RequestParam("key") String queryKey, HttpServletRequest request) {
		logger.info("<<<UserRecords>>>/"+"SearchPage/"+queryKey+"/"+request.getRemoteAddr());

		return "/WEB-INF/pages/search.jsp";
	}

	@RequestMapping(value = "/artist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchArtist(@RequestParam("key") String key, @RequestParam("currPage") int currPage,
			@RequestParam("perPage") int perPage, @RequestParam MultiValueMap<String, String> allRequestParams) throws IOException {
		System.out.println(allRequestParams);
		if (key == null || key.equals("")) {
			key = "*:*";
		}
		return MusicTagUtil.wrapResult(searchServiceImpl.searchArtist(key, currPage, perPage, allRequestParams));
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

	@RequestMapping(value = "/lyric", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchLyric(@RequestParam("key") String key, @RequestParam("currPage") int currPage,
			@RequestParam("perPage") int perPage) throws IOException {
		return MusicTagUtil.wrapResult(searchServiceImpl.searchLyric(key, currPage, perPage));
	}
}
