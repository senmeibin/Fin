package com.gwb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwb.entity.Schedule;
import com.gwb.mapper.ScheduleMapper;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	
	public List<Schedule> getAll() {
		return scheduleMapper.getAll();
	}
	
	public Schedule getOne(int id) {
		return scheduleMapper.getOne(id);
	}
	
	public void add(Schedule schedule) {
		scheduleMapper.insert(schedule);
	}
	
	public void edit(Schedule schedule) {
		scheduleMapper.update(schedule);
	}
	
	public void delete(int id) {
		scheduleMapper.delete(id);
	}
}