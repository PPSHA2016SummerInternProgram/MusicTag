package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.impl.StatisticsServiceImpl;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsServiceImpl statisticsServiceImpl;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(){
		return "/WEB-INF/pages/statistics.jsp";
	}

	@RequestMapping(value = "/artist-listeners", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistListeners() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistListeners());
	}

	@RequestMapping(value = "/artist-playcount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistPlaycount() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistPlaycount());
	}

	@RequestMapping(value = "/release-listeners", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseListeners() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.releaseListeners());
	}

	@RequestMapping(value = "/release-playcount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releasePlaycount() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.releasePlaycount());
	}

	@RequestMapping(value = "/recording-listeners", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> recordingListeners() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.recordingListeners());
	}

	@RequestMapping(value = "/recording-playcount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> recordingPlaycount() throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.recordingPlaycount());
	}
}
