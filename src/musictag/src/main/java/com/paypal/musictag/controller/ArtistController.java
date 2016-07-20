package com.paypal.musictag.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistServiceImpl;

    @RequestMapping(value = "/{gid}/profile", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> profile(@PathVariable("gid") String gid) {

        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.profile(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(),
                    ResponseCode.NOT_PROVIDED);
        }
    }

    @RequestMapping(value = "/{gid}/rel-links", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> relLinks(@PathVariable("gid") String gid) {

        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.relLinks(gid), null);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(),
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
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(),
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/release-groups", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseGroup(@PathVariable("gid") String gid) {
        try {
            return MusicTagUtil.createResultMap(true,
                    artistServiceImpl.releaseGroup(gid), ResponseCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(),
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/release-groups-paged", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> releaseGroupPaged(
            @PathVariable("gid") String gid,
            @RequestParam("cur-page") int curPage,
            @RequestParam("per-page") int perPage,
            @RequestParam("order-by") String orderBy,
            @RequestParam("direction") String direction) {
        try {
            System.out.println("curPage: " + curPage);
            return MusicTagUtil.createResultMap(true, artistServiceImpl
                    .releaseGroupPaged(gid, curPage, perPage, orderBy,
                            direction), ResponseCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(),
                    ResponseCode.ERR_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String artistOverview(ModelMap model) {
        return "/WEB-INF/pages/artist-overview.jsp";
    }
}
