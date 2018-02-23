package com.gwb.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwb.dto.MatchDto;
import com.gwb.dto.PointDto;
import com.gwb.entity.Match;
import com.gwb.entity.Member;
import com.gwb.entity.Point;
import com.gwb.mapper.MatchMapper;
import com.gwb.mapper.MemberMapper;
import com.gwb.mapper.PointMapper;

@Service
public class MatchService {
	
	@Autowired
	private MatchMapper matchMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private PointService pointService;
	
	private static final String DATE_FORMAT="yyyyMMdd" ;
	
	public List<Match> getTodayMatch() {
		LocalDate nowLocalDt = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String todayString = dtf.format(nowLocalDt);
		return matchMapper.getByMatchDate(todayString);
	}
	
	public List<Match> getMatchList(String match_date) {
		return matchMapper.getByMatchDate(match_date);
	}
	
	public List<MatchDto> getMatchDtoList(String match_date) {
		List<MatchDto> ret = new ArrayList<MatchDto>();
		
		List<Match> matches = matchMapper.getByMatchDate(match_date);
		List<Member> members = memberMapper.getAll();
		
		for(Match match : matches) {
			MatchDto matchDto = new MatchDto();
			BeanUtils.copyProperties(match, matchDto);
			for(Member member : members) {
				if(member.getId() == match.getWinner1()) {
					matchDto.setWinner1Name(member.getName());
				}
				if(member.getId() == match.getWinner2()) {
					matchDto.setWinner2Name(member.getName());
				}
				if(member.getId() == match.getLosser1()) {
					matchDto.setLosser1Name(member.getName());
				}
				if(member.getId() == match.getLosser2()) {
					matchDto.setLosser2Name(member.getName());
				}
				if(matchDto.getWinner1Name()!=null && matchDto.getWinner2Name()!=null
					&& matchDto.getLosser1Name()!=null && matchDto.getLosser2Name()!=null) {
					break;
				}
			}
			ret.add(matchDto);
		}
		return ret;
	}
	
	public void add(Match match) {
		if(match.getMatch_date() == null || match.getMatch_date().isEmpty()) {
			// 比赛日期
			LocalDate nowLocalDt = LocalDate.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
			String todayString = dtf.format(nowLocalDt);
			match.setMatch_date(todayString);
		}
		// 比赛积分
		pointService.caculateOneMatchPoint(match);
		
		// 更新
		matchMapper.insert(match);
	}
	
	public void edit(Match match) {
		matchMapper.update(match);
	}
	
	public void delete(Match match) {
		matchMapper.delete(match.getId());
	}

	public Match getOne(int id) {
		return matchMapper.getOne(id);
	}
	
}