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
import com.paypal.musictag.service.ReleaseGroupService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/release-group")
public class ReleaseGroupController {

	@Autowired
	private ReleaseGroupService releaseGroupServiceImpl;

	@RequestMapping(value = "/{gid}/releases", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releases(@PathVariable("gid") String gid) {
		try {
			return MusicTagUtil.createResultMap(true, releaseGroupServiceImpl.releases(gid), ResponseCode.SUCCESS);
		} catch (NetConnectionException | NetContentNotFoundException | JsonMappingException | MalformedURLException
				| ProtocolException e) {
			return MusicTagUtil.createResultMap(false, null, ResponseCode.getResponseCode(e));
		}
	}
}
