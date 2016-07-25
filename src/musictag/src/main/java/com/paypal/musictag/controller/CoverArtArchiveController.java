package com.paypal.musictag.controller;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.service.CoverArtArchiveService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/cover-art-archive")
public class CoverArtArchiveController {

	@Autowired
	private CoverArtArchiveService coverArtArchiveServiceImpl;

	@RequestMapping(value = "/release/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseCover(@PathVariable("gid") String gid) {
		try {
			return MusicTagUtil.createResultMap(true, coverArtArchiveServiceImpl.releaseCover(gid),
					ResponseCode.SUCCESS);
		} catch (JsonMappingException | NetConnectionException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/release-group/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroupCover(@PathVariable("gid") String gid) {
		try {
			return MusicTagUtil.createResultMap(true, coverArtArchiveServiceImpl.releaseGroupCover(gid),
					ResponseCode.SUCCESS);
		} catch (JsonMappingException | NetConnectionException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}
}
