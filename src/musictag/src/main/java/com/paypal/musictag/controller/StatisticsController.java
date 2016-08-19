package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String index() {
		return "/WEB-INF/pages/statistics.jsp";
	}

	@RequestMapping(value = "/distribution", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> distribution(Boolean refresh) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.distribution());
	}

	@RequestMapping(value = "/artist-listeners/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistListeners(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistListeners(gid));
	}

	@RequestMapping(value = "/artist-playcount/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistPlaycount(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistPlaycount(gid));
	}

	@RequestMapping(value = "/release-listeners/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseListeners(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.releaseListeners(gid));
	}

	@RequestMapping(value = "/release-playcount/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releasePlaycount(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.releasePlaycount(gid));
	}

	@RequestMapping(value = "/recording-listeners/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> recordingListeners(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.recordingListeners(gid));
	}

	@RequestMapping(value = "/recording-playcount/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> recordingPlaycount(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.recordingPlaycount(gid));
	}

	@RequestMapping(value = "/artist-area/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistArea(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistArea(gid));
	}

	@RequestMapping(value = "/artist/{gid}/release-dist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistReleaseYearlyDist(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistReleaseYearlyDist(gid));
	}
}