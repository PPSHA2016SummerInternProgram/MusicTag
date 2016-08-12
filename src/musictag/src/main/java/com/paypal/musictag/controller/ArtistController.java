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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.service.StatisticsService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/artist")
public class ArtistController {
	
	private static Logger logger = Logger.getLogger(ArtistController.class);  
	
	@Autowired
	private ArtistService artistServiceImpl;
	@Autowired
	private StatisticsService statisticsServiceImpl;

	@RequestMapping(value = "/{gid}/profile", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> profile(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(artistServiceImpl.profile(gid));
	}

	@RequestMapping(value = "/{gid}/rel-links", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> relLinks(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(artistServiceImpl.relLinks(gid));
	}

	@RequestMapping(value = "/{gid}/image", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> image(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(artistServiceImpl.image(gid));
	}

	@RequestMapping(value = "/{gid}/basic-info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> basicInfo(@PathVariable("gid") String gid) throws IOException {

		return MusicTagUtil.wrapResult(artistServiceImpl.basicInfo(gid));
	}

	@RequestMapping(value = "/{gid}/release-groups-old", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroup(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(artistServiceImpl.releaseGroup(gid));
	}

	@RequestMapping(value = "/{gid}/release-groups", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroupPaged(@PathVariable("gid") String gid, @RequestParam("cur-page") int curPage,
			@RequestParam("per-page") int perPage, @RequestParam("order-by") String orderBy,
			@RequestParam("direction") String direction) throws Exception {
		return MusicTagUtil.wrapResult(artistServiceImpl.releaseGroupPaged(gid, curPage, perPage, orderBy, direction));
	}

	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
	public String artistOverview(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {
		
		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/"+request.getRemoteAddr());
		
		return "/WEB-INF/pages/artist-overview.jsp";
	}
	
	/*
	================================================================
			statistics api for artist
	================================================================		
	*/
	/**
	 * 
	 * @param gid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{gid}/artist-credit-counts", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistCreditCount(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistCreditCount(gid));
	}
	
	@RequestMapping(value = "/{gid}/artist-areas", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistAreas(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.ArtistArea(gid));
	}
	@RequestMapping(value="/{gid}/tooltip-info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> tooltipInfo(@PathVariable("gid") String gid) throws IOException{
		return MusicTagUtil.wrapResult(artistServiceImpl.tooltipInfo(gid));
	}
	
	@RequestMapping(value="/{sid}/target-artist/{tid}/cooperations", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistCooperations(@PathVariable("sid") Integer sourceId, @PathVariable("tid") Integer targetId) {
		return MusicTagUtil.wrapResult(artistServiceImpl.artistCooperations(sourceId, targetId));
	}

}
