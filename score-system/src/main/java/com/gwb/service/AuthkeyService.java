package com.gwb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwb.entity.Authkey;
import com.gwb.mapper.AuthkeyMapper;

@Service
public class AuthkeyService {
	
	@Autowired
	private AuthkeyMapper authkeyMapper;
	
	public Authkey getOne(String key) {
		return authkeyMapper.getOne(key);
	}
	
	public Boolean checkMatchEntryAuth(String auth) {
        Authkey authkey = authkeyMapper.getOne(auth);
        if(authkey==null) {
        	return false;
        }
       
        return authkey.getMatchEntryAuth();
	}
	
	public Boolean checkAdminAuth(String auth) {
        Authkey authkey = authkeyMapper.getOne(auth);
        if(authkey==null) {
        	return false;
        }
       
        return authkey.getAdminAuth();
	}
}