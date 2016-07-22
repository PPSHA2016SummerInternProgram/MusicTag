package com.paypal.musictag.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ReleaseService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;


@Controller
@RequestMapping("/release")
public class ReleaseController {
	
	
	private static final Logger logger = LoggerFactory
            .getLogger(ReleaseController.class);
	
	@Autowired
    private ReleaseService releaseServiceImpl;
	
	@RequestMapping(value = "/{gid}/tracklist", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> tracklist(@PathVariable("gid") String gid) {

        logger.info("tracklist");
        try {
            return MusicTagUtil.createResultMap(true,
                    releaseServiceImpl.vote(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.NOT_PROVIDED);
        }
	}
	
	
	@RequestMapping(value = "/{gid}/artistinfo", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> artistinfo(@PathVariable("gid") String gid) {

        logger.info("artistinfo");
        try {
            return MusicTagUtil.createResultMap(true,
                    releaseServiceImpl.artistinfo(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null, 
                    ResponseCode.NOT_PROVIDED);
        }
	}
	
	
	@RequestMapping(value = "/{gid}/releasevote", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> releasevote(@PathVariable("gid") String gid) {

        logger.info("releasevote");
        try {
            return MusicTagUtil.createResultMap(true,
                    releaseServiceImpl.releasevote(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null, 
                    ResponseCode.NOT_PROVIDED);
        }
	}
	

    @RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String tracklist() {
        return "/WEB-INF/pages/release.jsp";
    }
    
    
    
/*    
	@RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String artistOverview(ModelMap model) {
        return "/WEB-INF/pages/song.jsp";

    }
    */
}

