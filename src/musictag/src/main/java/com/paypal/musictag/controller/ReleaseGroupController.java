package com.paypal.musictag.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ReleaseGroupService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/release-group")
public class ReleaseGroupController {
    
    private static final Logger logger = LoggerFactory.getLogger(ReleaseGroupController.class);

    @Autowired
    private ReleaseGroupService releaseGroupServiceImpl;

    @RequestMapping(value = "/{gid}/releases", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releases(@PathVariable("gid") String gid) {
        try{
            return MusicTagUtil.createResultMap(true,
                    releaseGroupServiceImpl.releases(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            // TODO: better handling of exceptions
            logger.error(null, e);
            return MusicTagUtil.createResultMap(false, null,
                    ResponseCode.NOT_PROVIDED);
        }
    }
}
