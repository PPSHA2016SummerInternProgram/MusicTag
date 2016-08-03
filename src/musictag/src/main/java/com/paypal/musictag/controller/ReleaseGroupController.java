package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.CoverArtArchiveService;
import com.paypal.musictag.service.ReleaseGroupService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/release-group")
public class ReleaseGroupController {

	@Autowired
	private ReleaseGroupService releaseGroupServiceImpl;
	@Autowired
	private CoverArtArchiveService coverArtArchiveServiceImpl;

	@RequestMapping(value = "/{gid}/releases", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releases(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(releaseGroupServiceImpl.releases(gid));
	}

	@RequestMapping(value = "/{gid}/image", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> image(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(coverArtArchiveServiceImpl.releaseGroupCover(gid));
	}
}
