package com.paypal.musictag.controller;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.service.ReleaseService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/release")
public class ReleaseController {

	@Autowired
	private ReleaseService releaseServiceImpl;

	@RequestMapping(value = "/{gid}/tracklist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> tracklist(@PathVariable("gid") String gid) {

		try {
			return MusicTagUtil.createResultMap(true, releaseServiceImpl.vote(gid), ResponseCode.SUCCESS);
		} catch (NetConnectionException | NetContentNotFoundException | JsonMappingException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/artistinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistinfo(@PathVariable("gid") String gid) {
		try {
			return MusicTagUtil.createResultMap(true, releaseServiceImpl.artistinfo(gid), ResponseCode.SUCCESS);
		} catch (Exception e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/releasevote", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releasevote(@PathVariable("gid") String gid) {

		try {
			return MusicTagUtil.createResultMap(true, releaseServiceImpl.releasevote(gid), ResponseCode.SUCCESS);
		} catch (Exception e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	public String tracklistpage(ModelMap model) {
		return "/WEB-INF/pages/release.jsp";
	}

}
