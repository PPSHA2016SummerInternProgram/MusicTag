package com.paypal.musictag.service.impl;

import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import com.paypal.musictag.service.ArtistService;

import org.musicbrainz.controller.Artist;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.MBWS2Exception;

@Service("ArtistService")
public class ArtistServiceImpl implements ArtistService {
    public Map<String, Object> findById(String id) {
        Map<String, Object> res = new HashMap<String, Object>();

        try {
            Artist artist = new Artist();
            ArtistWs2 artistWs2 = artist.lookUp(id);
            res.put("name", artistWs2.getName());
            res.put("gender", artistWs2.getGender());
            res.put("gender", artistWs2.getCountry());
            res.put("area", artistWs2.getArea());
        } catch (MBWS2Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}
