package com.heqiao2010.aopcacheutil.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.heqiao2010.aopcacheutil.starter.entity.UserDO;
import com.heqiao2010.aopcacheutil.starter.func.UserMgr;

@RestController
public class UserController {

	@Autowired
	private UserMgr userMgr;

	@ResponseBody
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String add(UserDO u) {
		userMgr.add(u);
		return "OK";
	}

	@ResponseBody
	@RequestMapping(value = "/user/query", method = RequestMethod.GET)
	public UserDO query(@RequestParam("name") String name, @RequestParam("age") int age) {
		UserDO u = userMgr.query(name, age);
		return u;
	}

	@ResponseBody
	@RequestMapping(value = "/user/update", method = RequestMethod.PUT)
	public UserDO update(UserDO u) {
		UserDO newUser = userMgr.update(u);
		return newUser;
	}

	@ResponseBody
	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
	public String delete(@RequestParam("name") String name, @RequestParam("age") int age) {
		UserDO u = new UserDO();
		u.setName(name);
		u.setAge(age);
		userMgr.delete(u);
		return "OK";
	}
}
