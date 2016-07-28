package com.paypal.musictag.dao.usingwebservice.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.exception.NetBadRequestException;
import com.paypal.musictag.exception.NetConnectionException;
import com.paypal.musictag.exception.NetContentNotFoundException;
import com.paypal.musictag.values.StaticValues;

/**
 * 
 * @author shilzhang
 *
 */
public class MusicTagServiceAPITest {

	@Test
	public void testMusicTagServiceAPI() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("artist", StaticValues.artistGid0);
		sendSomeRequest(params);
		params.put("artist", StaticValues.artistGid1);
		sendSomeRequest(params);
	}

	private void sendSomeRequest(Map<String, String> params) throws IOException {
		MusicTagServiceAPI.sendRequest("release", params);
		MusicTagServiceAPI.sendRequest("release-group", params);
		MusicTagServiceAPI.sendRequest("work", params);
		MusicTagServiceAPI.sendRequest("recording", params);
	}

	//	NetContentNotFoundException not tested.
	
	@Test(expected = NetBadRequestException.class)
	public void testThrowNetBadRequest() throws NetConnectionException, NetContentNotFoundException,
			JsonMappingException, MalformedURLException, ProtocolException, NetBadRequestException {
		MusicTagServiceAPI.sendRequest("noEntity", null);
	}
}
