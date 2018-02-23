package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.dto.PointDto;
import com.gwb.entity.Member;
import com.gwb.entity.WinPer;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.AuthkeyService;
import com.gwb.service.PointService;

@RestController
public class PointController {
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private AuthkeyService authkeyService;
	
    @RequestMapping(value="/point/get")
    public List<PointDto> getPointDtoList(String match_date) {
    	return pointService.getPointDtoList(match_date);
    }
    
    @RequestMapping(value="/point/getWinPerRank")
    public List<WinPer> getWinPerRank(String match_date) {
    	return pointService.getWinPerRank(match_date);
    }
    
    @RequestMapping(value="/point/getMatchDate")
    public List<String> getAllMatchDate() {
    	return pointService.getAllMatchDate();
    }
    
    @RequestMapping(value="/point/getRecentMember")
    public List<Member> getRecentMember() {
    	return pointService.getRecentMember();
    }
    
    @RequestMapping(value="/point/caculatePoint")
    public void caculatePoint(@RequestHeader("Authorization") String auth) throws Exception {
        if(!authkeyService.checkAdminAuth(auth)) {
        	throw new Exception("No admin permission.");
        }
    	pointService.processMatchResults();
    }
}