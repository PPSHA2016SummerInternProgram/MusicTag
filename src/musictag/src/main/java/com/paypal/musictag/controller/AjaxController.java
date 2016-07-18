package com.paypal.musictag.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.musictag.service.HelloService;
import com.paypal.musictag.util.MusicTagUtil;
import com.paypal.musictag.util.ResponseCode;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    private HelloService helloServiceImpl;

    /**
     * 这只是一个例子，讲述如何在controller中调用service。
     * 
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> hello() {

        try {
            return MusicTagUtil.createResultMap(true, helloServiceImpl.hello(),
                    ResponseCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, null, e.getMessage(), ResponseCode.ERR_NOT_FOUND);
        }
    }

    /**
     * 这只是一个例子，讲述如何传参。
     * 
     * @param a
     * @param b
     * @param operation
     * @return
     */
    @RequestMapping(value = "/params", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> params(@RequestParam Double a,
            @RequestParam Double b, String operation) {

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("a", a);
        data.put("b", b);
        data.put("operation", operation);

        double result = 0.0;
        try {
            if ("+".equals(operation)) {
                result = a + b;
            } else if ("-".equals(operation)) {
                result = a - b;
            } else if ("*".equals(operation)) {
                result = a * b;
            } else if ("/".equals(operation)) {
                result = a / b;
            } else {
                throw new Exception("Cannot support the operation " + operation);
            }
            data.put("result", result);
            return MusicTagUtil.createResultMap(true, data, ResponseCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return MusicTagUtil.createResultMap(false, data, e.getMessage(), ResponseCode.ERR_NOT_FOUND);
        }
    }
}
