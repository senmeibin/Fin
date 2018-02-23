package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.entity.Authkey;
import com.gwb.entity.Member;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.AuthkeyService;
import com.gwb.service.MemberService;

@RestController
public class AuthkeyController {
	
	@Autowired
	private AuthkeyService authkeyService;
	
    @RequestMapping("/authkey/getOne")
    public Authkey getOne(String key) {
    	return authkeyService.getOne(key);
    }
}