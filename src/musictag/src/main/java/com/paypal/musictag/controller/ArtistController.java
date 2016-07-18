package com.paypal.musictag.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.ArtistService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/artist")
public class ArtistController {
	@Autowired
	private ArtistService artistServiceImpl;

	@RequestMapping(value = "/{gid}/basic-info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> basicInfo(@PathVariable("gid") String gid) {

		try {
			return MusicTagUtil.createResultMap(true, artistServiceImpl.basicInfo(gid), ResponseCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage(), ResponseCode.ERR_NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{gid}/release-groups", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> releaseGroup(@PathVariable("gid") String gid) {
		try {
			return MusicTagUtil.createResultMap(true, artistServiceImpl.releaseGroup(gid), ResponseCode.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return MusicTagUtil.createResultMap(false, null, e.getMessage(), ResponseCode.ERR_NOT_FOUND);
		}
	}

    @RequestMapping(value = "/{gid}/", method = RequestMethod.GET)
    public String artistOverview(ModelMap model) {
        return "/WEB-INF/pages/artist-overview.jsp";
    }
}
