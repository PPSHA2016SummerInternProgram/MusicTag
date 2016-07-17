package com.paypal.musictag.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.musictag.dao.usingdb.HelloMapper;
import com.paypal.musictag.service.HelloService;

@Service("helloServiceImpl")
public class HelloServiceImpl implements HelloService {

	@Autowired
	HelloMapper helloMapper;

	public Map<String, Object> hello() throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("location", "Hello! I'm created in HelloServiceImpl.hello");
		result.put("artistAmount", helloMapper.countArtist());
		return result;
	}

}
