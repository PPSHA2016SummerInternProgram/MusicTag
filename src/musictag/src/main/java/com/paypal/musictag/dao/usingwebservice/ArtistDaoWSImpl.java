package com.paypal.musictag.dao.usingwebservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.ArtistDao;

@Service("artistDaoWSImpl")
public class ArtistDaoWSImpl implements ArtistDao {

	@Override
    public Map<String, Object> basicInfo(String artistGid) throws IOException {
		Map<String, String> params = new HashMap<>();
		params.put("inc", "url-rels");
        Map<String, Object> res = MusicTagServiceAPI.sendRequest("artist/" + artistGid, params);
        res.putAll(MusicTagPrivateAPI.getArtistCommonsImage(artistGid));
        res.putAll(MusicTagPrivateAPI.getArtistWikiProfle(artistGid));
        return res;
    }

	@Override
    public Map<String, Object> releaseGroup(String artistGid)
            throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("artist", artistGid);
        params.put("limit", "100");
        return MusicTagServiceAPI.sendRequest("release-group/", params);
    }
}
