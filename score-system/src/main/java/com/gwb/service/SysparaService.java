package com.gwb.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwb.entity.Match;
import com.gwb.entity.Member;
import com.gwb.entity.Syspara;
import com.gwb.mapper.MatchMapper;
import com.gwb.mapper.PointMapper;
import com.gwb.mapper.SysparaMapper;

@Service
public class SysparaService {
	
	@Autowired
	private SysparaMapper sysparaMapper;
	
	private static final String DATE_FORMAT="yyyyMMdd" ;
	
	public void editLastBatchDate(Syspara lastBatchDate) throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate last_batch_date_locatDt = LocalDate.parse(lastBatchDate.getValue(),dtf);
		LocalDate nowLocalDt = LocalDate.now();
		
		if(last_batch_date_locatDt.isAfter(nowLocalDt) || Duration.between(nowLocalDt.atStartOfDay(), last_batch_date_locatDt.atStartOfDay()).toDays()>90) {
			throw new Exception("只能修改到90天以内的日期。");
		}
		
		sysparaMapper.update(lastBatchDate);
	}
	
	public Syspara getLastBatchDate() {
		return sysparaMapper.getOne("last_batch_date");
	}
}