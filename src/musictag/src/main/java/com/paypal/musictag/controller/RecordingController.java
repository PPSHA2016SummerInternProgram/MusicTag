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
import com.paypal.musictag.service.RecordingService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/recording")
public class RecordingController {

	@Autowired
	private RecordingService recordingServiceImpl;

	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> basic(@PathVariable("gid") String recordingId) {
		try {
			return MusicTagUtil.createResultMap(true, recordingServiceImpl.basic(recordingId), ResponseCode.SUCCESS);
		} catch (NetConnectionException | JsonMappingException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/releases", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releases(@PathVariable("gid") String recordingId) {
		try {
			return MusicTagUtil.createResultMap(true, recordingServiceImpl.releases(recordingId), ResponseCode.SUCCESS);
		} catch (NetConnectionException | JsonMappingException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/work-artist-rels", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> workArtistRels(@PathVariable("gid") String recordingId) {
		try {
			return MusicTagUtil.createResultMap(true, recordingServiceImpl.workArtistRels(recordingId),
					ResponseCode.SUCCESS);
		} catch (NetConnectionException | JsonMappingException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}

	@RequestMapping(value = "/{gid}/full", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> full(@PathVariable("gid") String recordingId) {
		try {
			return MusicTagUtil.createResultMap(true, recordingServiceImpl.full(recordingId), ResponseCode.SUCCESS);
		} catch (NetConnectionException | JsonMappingException | NetContentNotFoundException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}
}
