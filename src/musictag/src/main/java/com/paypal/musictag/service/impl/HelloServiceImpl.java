package com.paypal.musictag.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.musictag.service.HelloService;

@Service("helloServiceImpl")
public class HelloServiceImpl implements HelloService {

	public Map<String, Object> hello() throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("location", "Hello! I'm created in HelloServiceImpl.hello");
		return result;
	}

}
