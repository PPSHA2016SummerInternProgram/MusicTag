package com.paypal.musictag.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/artist")
public class ArtistController {
	@Autowired
	private ArtistService artistServiceImpl;

	@RequestMapping(value = "/basicInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> basicInfo(@RequestParam String gid) {

		try {
			return MusicTagUtil.createResultMap(true, artistServiceImpl.basicInfo(gid), null);
		} catch (Exception e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage());
		}
	}

	@RequestMapping(value = "/releaseGroup", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroup(@RequestParam String gid) {
		try {
			return MusicTagUtil.createResultMap(true, artistServiceImpl.releaseGroup(gid), null);
		} catch (Exception e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage());
		}
	}
}
