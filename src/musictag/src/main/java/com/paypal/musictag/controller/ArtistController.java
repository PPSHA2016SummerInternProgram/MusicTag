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
import com.paypal.musictag.util.CooperationType;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/artist")
public class ArtistController {
	
	private static Logger logger = Logger.getLogger(ArtistController.class);  
	
	@Autowired
	private ArtistService artistServiceImpl;
	@Autowired
	private StatisticsService statisticsServiceImpl;

	@RequestMapping(value = "/{gid}/distribution/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> distribution(@PathVariable("gid") String gid, @PathVariable("type") String type)
			throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.distributionDetail(gid, type));
	}
	
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

	@RequestMapping(value = "/{gid}/relationship", method = RequestMethod.GET)
	public String artistRelationship(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {
		
		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/relationship"+request.getRemoteAddr());
		
		return "/WEB-INF/pages/artist_relationship.jsp";
	}

	@RequestMapping(value = "/{gid}/popularity", method = RequestMethod.GET)
	public String artistPopularity(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {

		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/popularity"+request.getRemoteAddr());

		return "/WEB-INF/pages/artist_popularity.jsp";
	}

	@RequestMapping(value = "/{gid}/influence", method = RequestMethod.GET)
	public String artistInfluence(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {

		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/influence"+request.getRemoteAddr());

		return "/WEB-INF/pages/artist_influence.jsp";
	}

	@RequestMapping(value = "/{gid}/productivity", method = RequestMethod.GET)
	public String artistProductivity(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {

		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/productivity"+request.getRemoteAddr());

		return "/WEB-INF/pages/artist_productivity.jsp";
	}

	@RequestMapping(value = "/{gid}/active-span", method = RequestMethod.GET)
	public String artistActiveSpan(ModelMap model, @PathVariable("gid") String gid, HttpServletRequest request) {

		logger.info("<<<UserRecords>>>/"+"ArtistPage/"+gid+"/active_span"+request.getRemoteAddr());

		return "/WEB-INF/pages/artist_active_span.jsp";
	}

	@RequestMapping(value = "/{gid}/similar", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> similarArtist(@PathVariable("gid") String gid) throws Exception {
		return MusicTagUtil.wrapResult(artistServiceImpl.similarArtist(gid));
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
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistAreaCount(gid));
	}
	
	@RequestMapping(value = "/{gid}/artist-areas-details", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistAreasDetails(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistAreaDetails(gid));
	}
	
	@RequestMapping(value = "/{gid}/artist-edit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistEdit(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistEdit(gid));
	}
	
	@RequestMapping(value = "/{gid}/artist-lyricists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistLyricists(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistLyricist(gid));
	}
	
	@RequestMapping(value = "/{gid}/artist-composers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistComposers(@PathVariable("gid") String gid) throws IOException {
		return MusicTagUtil.wrapResult(statisticsServiceImpl.artistComposer(gid));
	}
	
	@RequestMapping(value="/{gid}/tooltip-info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> tooltipInfo(@PathVariable("gid") String gid) throws IOException{
		return MusicTagUtil.wrapResult(artistServiceImpl.tooltipInfo(gid));
	}
	
	/**
	 * 
	 * @param sourceId
	 * @param targetId
	 * @param type: credit, lyricist or composer.
	 * @return
	 */
	@RequestMapping(value="/{sid}/target-artist/{tid}/type/{type}/cooperations", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> artistCooperations(@PathVariable("sid") Integer sourceId, @PathVariable("tid") Integer targetId, @PathVariable("type") String type) {
		return MusicTagUtil.wrapResult(artistServiceImpl.artistCooperations(sourceId, targetId, CooperationType.valueOf(type.toUpperCase())));
	}

}
