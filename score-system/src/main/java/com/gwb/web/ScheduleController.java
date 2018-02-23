package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.entity.Member;
import com.gwb.entity.Schedule;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.AuthkeyService;
import com.gwb.service.MemberService;
import com.gwb.service.ScheduleService;

@RestController
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private AuthkeyService authkeyService;
	
	@RequestMapping("/schedule/getAll")
	public List<Schedule> getAll() {
		return scheduleService.getAll();
	}
	
	@RequestMapping("/schedule/getOne")
	public Schedule getOne(int id) {
		return scheduleService.getOne(id);
	}
	
	@RequestMapping("/schedule/add")
	public void add(@RequestHeader("Authorization") String auth,@RequestBody Schedule schedule) throws Exception {
        if(!authkeyService.checkAdminAuth(auth)) {
        	throw new Exception("No admin permission.");
        }
		scheduleService.add(schedule);
	}
	
	@RequestMapping("/schedule/edit")
	public void update(@RequestHeader("Authorization") String auth,@RequestBody Schedule schedule) throws Exception {
        if(!authkeyService.checkAdminAuth(auth)) {
        	throw new Exception("No admin permission.");
        }
		scheduleService.edit(schedule);
	}
	
	@RequestMapping(value="/schedule/delete")
	public void delete(@RequestHeader("Authorization") String auth,@RequestBody int id) throws Exception {
        if(!authkeyService.checkAdminAuth(auth)) {
        	throw new Exception("No admin permission.");
        }
		scheduleService.delete(id);
	}
}