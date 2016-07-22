package com.paypal.musictag.controller;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;
import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    private static final Logger logger = LoggerFactory
            .getLogger(ArtistController.class);

    @Autowired
    private ArtistService artistServiceImpl;

    @RequestMapping(value = "/{gid}/profile", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> profile(@PathVariable("gid") String gid) {

        logger.info("profile");
        try {
			return MusicTagUtil.createResultMap(true,
			        artistServiceImpl.profile(gid), ResponseCode.SUCCESS);
		} catch (NetConnectionException e) {
			logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.NOT_PROVIDED);
		}
    }

    @RequestMapping(value = "/{gid}/rel-links", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> relLinks(@PathVariable("gid") String gid) {

    	 try {
			return MusicTagUtil.createResultMap(true,
			         artistServiceImpl.relLinks(gid), null);
		} catch (NetConnectionException | NetContentNotFoundException | JsonMappingException | MalformedURLException
				| ProtocolException e1) {
			logger.error(null, e1);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.NOT_PROVIDED);
		}
    }

    @RequestMapping(value = "/{gid}/image", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> image(@PathVariable("gid") String gid) {

        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.image(gid), null);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.NOT_PROVIDED);
        }
    }

    @RequestMapping(value = "/{gid}/basic-info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> basicInfo(@PathVariable("gid") String gid) {

        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.basicInfo(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/release-groups-old", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseGroup(@PathVariable("gid") String gid) {
        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.releaseGroup(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/release-groups", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseGroupPaged(
            @PathVariable("gid") String gid,
            @RequestParam("cur-page") int curPage,
            @RequestParam("per-page") int perPage,
            @RequestParam("order-by") String orderBy,
            @RequestParam("direction") String direction) {
        try {
            return MusicTagUtil.createResultMap(true, artistServiceImpl
                    .releaseGroupPaged(gid, curPage, perPage, orderBy,
                            direction), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String artistOverview(ModelMap model) {
        return "/WEB-INF/pages/artist-overview.jsp";
    }
}
