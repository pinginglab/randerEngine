package com.pingsec.dev.service;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "utils", url = "${addr.url}")
public interface utilsClient {
    //feign独有的注解方式
    @RequestLine("GET /user/index")
    String index();

    @RequestMapping(value = "/get0/{id}", method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);

    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    User get1(@RequestParam("id") Long id, @RequestParam("name") String name);

    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    User get2(@RequestParam Map<String, Object> map);

    @RequestMapping(value = "/hello2", method=RequestMethod.GET)
    User hello2(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3", method=RequestMethod.POST)
    String hello3(@RequestBody User user);
}
