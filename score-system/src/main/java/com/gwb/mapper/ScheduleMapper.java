package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gwb.entity.Member;
import com.gwb.entity.Schedule;

public interface ScheduleMapper {
	
	@Select("SELECT * FROM schedule")
	List<Schedule> getAll();
	
	@Select("SELECT * FROM schedule WHERE id = #{id}")
	Schedule getOne(int id);

	@Insert("INSERT INTO schedule(training_date,training_time) VALUES(#{training_date}, #{training_time})")
	void insert(Schedule schedule);

	@Update("UPDATE schedule SET training_date=#{training_date},training_time=#{training_time} WHERE id =#{id}")
	void update(Schedule schedule);

	@Delete("DELETE FROM schedule WHERE id =#{id}")
	void delete(int id);
}