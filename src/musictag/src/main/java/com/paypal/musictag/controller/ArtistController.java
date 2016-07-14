package com.paypal.musictag.controller;

import com.paypal.musictag.service.ArtistService;
import java.util.Map;

import com.paypal.musictag.util.MusicTagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistServiceImpl;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> fetchArtist(@PathVariable String id){
        try {
            return MusicTagUtil.createResultMap(true, artistServiceImpl.findById(id), null);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(true, null, e.getMessage());
        }
    }
}
