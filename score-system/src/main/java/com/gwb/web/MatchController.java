package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.dto.MatchDto;
import com.gwb.dto.PointDto;
import com.gwb.entity.Authkey;
import com.gwb.entity.Match;
import com.gwb.entity.Member;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.AuthkeyService;
import com.gwb.service.MatchService;
import com.gwb.service.PointService;

@RestController
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private AuthkeyService authkeyService;
	
    @RequestMapping(value="/match/getOne")
    public Match getOne(int id) {
    	return matchService.getOne(id);
    }
    
    @RequestMapping(value="/match/getTodayMatch")
    public List<Match> getTodayMatch() {
    	return matchService.getTodayMatch();
    }
    
    @RequestMapping(value="/match/getMatchList")
    public List<Match> getMatchList(String match_date) {
    	return matchService.getMatchList(match_date);
    }
    
    @RequestMapping(value="/match/getMatchDtoList")
    public List<MatchDto> getMatchDtoList(String match_date) {
    	return matchService.getMatchDtoList(match_date);
    }

    
    @RequestMapping(value="/match/add")
    public void add(@RequestHeader("Authorization") String auth,@RequestBody Match match) throws Exception {
        if(!authkeyService.checkMatchEntryAuth(auth)) {
        	throw new Exception("No match entry permission.");
        }
        matchService.add(match);
    }
    
    @RequestMapping(value="/match/edit")
    public void edit(@RequestHeader("Authorization") String auth,@RequestBody Match match) throws Exception {
        if(!authkeyService.checkMatchEntryAuth(auth)) {
        	throw new Exception("No match entry permission.");
        }
        matchService.edit(match);
    }
    
    @RequestMapping(value="/match/delete")
    public void delete(@RequestHeader("Authorization") String auth,@RequestBody Match match) throws Exception {
        if(!authkeyService.checkMatchEntryAuth(auth)) {
        	throw new Exception("No match entry permission.");
        }
        matchService.delete(match);
    }
}