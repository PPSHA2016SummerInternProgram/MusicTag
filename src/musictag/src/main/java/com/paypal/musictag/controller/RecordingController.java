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

import com.paypal.musictag.service.RecordingService;
import com.paypal.musictag.util.MusicTagUtil;

@Controller
@RequestMapping("/recording")
public class RecordingController {

	private static Logger logger = Logger.getLogger(RecordingController.class);  
    @Autowired
    private RecordingService recordingServiceImpl;
    
    @RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String recording( ModelMap model, @PathVariable("gid") String recordingId, HttpServletRequest request){
    	
    	logger.info("<<<UserRecords>>>/"+"RecordingPage/"+recordingId+"/"+request.getRemoteAddr());
    	
    	return "/WEB-INF/pages/song.jsp";
    }
    
    @RequestMapping(value = "/{gid}/basic-info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> basic(@PathVariable("gid") String recordingId) throws IOException {
            return MusicTagUtil.wrapResult(recordingServiceImpl.basic(recordingId));
    }

    @RequestMapping(value = "/{gid}/releases", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releases(@PathVariable("gid") String recordingId) throws IOException{
            return MusicTagUtil.wrapResult(recordingServiceImpl.releases(recordingId));
    }

    @RequestMapping(value = "/{gid}/work-artist-rels", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> workArtistRels(@PathVariable("gid") String recordingId) throws IOException {
            return MusicTagUtil.wrapResult(recordingServiceImpl.workArtistRels(recordingId));
    }

    @RequestMapping(value = "/{gid}/full", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> full(@PathVariable("gid") String recordingId) throws IOException {
            return MusicTagUtil.wrapResult(recordingServiceImpl.full(recordingId));
    }
}
