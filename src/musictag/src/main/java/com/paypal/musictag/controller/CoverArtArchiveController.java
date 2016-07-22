package com.paypal.musictag.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.CoverArtArchiveService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/cover-art-archive")
public class CoverArtArchiveController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);
    
    @Autowired
    private CoverArtArchiveService coverArtArchiveServiceImpl;

    @RequestMapping(value = "/release/{gid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseCover(@PathVariable("gid") String gid) {
        try {
            return MusicTagUtil.createResultMap(true,
                    coverArtArchiveServiceImpl.releaseCover(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null, ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/release-group/{gid}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseGroupCover(@PathVariable("gid") String gid) {
        try {
            return MusicTagUtil.createResultMap(true,
                    coverArtArchiveServiceImpl.releaseGroupCover(gid), ResponseCode.SUCCESS);
        } catch (IOException e) {
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null, ResponseCode.ERR_NOT_FOUND);
        }
    }
}
