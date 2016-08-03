package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.CoverArtArchiveService;
import com.paypal.musictag.service.ReleaseService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/release")
public class ReleaseController {
	
	private static Logger logger = Logger.getLogger(ReleaseController.class);  

	@Autowired
	private ReleaseService releaseServiceImpl;
	@Autowired
	private CoverArtArchiveService coverArtArchiveServiceImpl;

	@RequestMapping(value = "/{gid}/tracklist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> tracklist(@PathVariable("gid") String gid) throws IOException {

		return MusicTagUtil.wrapResult(releaseServiceImpl.vote(gid));
	}

	@RequestMapping(value = "/{gid}/artistinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistinfo(@PathVariable("gid") String gid) throws IOException {
			return MusicTagUtil.wrapResult(releaseServiceImpl.artistinfo(gid));
	}

	@RequestMapping(value = "/{gid}/releasevote", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releasevote(@PathVariable("gid") String gid) throws IOException {

			return MusicTagUtil.wrapResult(releaseServiceImpl.releasevote(gid));
	}

	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	public String tracklistpage(ModelMap model,@PathVariable("gid") String gid, HttpServletRequest request) {

		logger.info("<<<UserRecords>>>/"+"ReleasePage/"+gid+"/"+request.getRemoteAddr());
		
		return "/WEB-INF/pages/release.jsp";
	}
	
	@RequestMapping(value = "/{gid}/image", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> image(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(coverArtArchiveServiceImpl.releaseCover(gid));
	}
	

}
