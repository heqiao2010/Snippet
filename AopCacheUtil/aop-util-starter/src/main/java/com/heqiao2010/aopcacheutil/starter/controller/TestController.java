package com.heqiao2010.aopcacheutil.starter.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.heqiao2010.aopcacheutil.core.annotation.Cache;
import com.heqiao2010.aopcacheutil.core.annotation.Evict;
import com.heqiao2010.aopcacheutil.core.annotation.Key;
import com.heqiao2010.aopcacheutil.core.annotation.Param;
import com.heqiao2010.aopcacheutil.core.annotation.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam("name") String name) {
        return "Hello " + name + " random: " + UUID.randomUUID().toString();
    }

    @ResponseBody
    @Cache(name = "random-test", timeUnit = TimeUnit.SECONDS, ttl = 1000, keys = { @Key("#{0}"), @Key("#{1}") })
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public String cacheByName(@RequestParam("name") String name, @RequestParam("age") int age) {
        return "Hello " + name + " random: " + UUID.randomUUID().toString();
    }

    @ResponseBody
    @Update(name = "random-test", timeUnit = TimeUnit.SECONDS, ttl = 1000, keys = { @Key("#{0}"), @Key("#{1}") })
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateByName(@RequestParam("name") String name, @RequestParam("age") int age) {
        return "Hello " + name + " random: " + UUID.randomUUID().toString();
    }

    @ResponseBody
    @Evict(name = "random-test", keys = { @Key("#{cacheName}"), @Key("#{1}") })
    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    public String evictByName(@RequestParam("name") @Param("cacheName") String name, @RequestParam("age") int age) {
        return "OK";
    }
}
