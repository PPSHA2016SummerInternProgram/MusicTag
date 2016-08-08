package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.SuggestService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/suggest")
public class SuggestController {

	@Autowired
	private SuggestService suggestServiceImpl;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> suggestAll(@RequestParam("query") String key, @RequestParam(value="count", defaultValue="-1")int count)
			throws IOException {
		return MusicTagUtil.wrapResult(suggestServiceImpl.suggestAll(key, count));
	}
}
