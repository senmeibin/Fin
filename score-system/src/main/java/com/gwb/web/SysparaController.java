package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.entity.Authkey;
import com.gwb.entity.Member;
import com.gwb.entity.Syspara;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.AuthkeyService;
import com.gwb.service.MemberService;
import com.gwb.service.SysparaService;

@RestController
public class SysparaController {
	
	@Autowired
	private SysparaService sysparaService;
	
	@Autowired
	private AuthkeyService authkeyService;
	
    @RequestMapping("/syspara/getLastBatchDate")
    public Syspara getLastBatchDate() {
    	return sysparaService.getLastBatchDate();
    }
    
    @RequestMapping("/syspara/editLastBatchDate")
    public void editLastBatchDate(@RequestHeader("Authorization") String auth,@RequestBody Syspara lastBatchDateObj) throws Exception {
        if(!authkeyService.checkAdminAuth(auth)) {
        	throw new Exception("No admin permission.");
        }
    	sysparaService.editLastBatchDate(lastBatchDateObj);
    }
}