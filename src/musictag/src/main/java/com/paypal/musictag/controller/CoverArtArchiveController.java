package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.CoverArtArchiveService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/coverArtArchive")
public class CoverArtArchiveController {
	@Autowired
	private CoverArtArchiveService coverArtArchiveServiceImpl;
	
	@RequestMapping(value="/release", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseCover(@RequestParam String gid){
		try {
			return MusicTagUtil.createResultMap(true, coverArtArchiveServiceImpl.releaseCover(gid), null);
		} catch (Exception e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage());
		}
	}
	
	@RequestMapping(value="/release-group", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroupCover(@RequestParam String gid){
		try {
			return MusicTagUtil.createResultMap(true, coverArtArchiveServiceImpl.releaseGroupCover(gid), null);
		} catch (IOException e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage());
		}
	}
}
