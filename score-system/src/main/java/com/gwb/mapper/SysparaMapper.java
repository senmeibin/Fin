package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gwb.entity.Point;
import com.gwb.entity.Syspara;

public interface SysparaMapper {
	
	@Select("SELECT * FROM syspara")
	List<Syspara> getAll();
	
	@Select("SELECT * FROM syspara WHERE parameter = #{parameter}")
	Syspara getOne(String parameter);
	
	@Select("SELECT value FROM syspara WHERE parameter = #{parameter} FOR UPDATE")
	String getOneForUpdate(String parameter);

	@Update("UPDATE syspara SET value=#{value} WHERE parameter = #{parameter}")
	void update(Syspara syspara);
	
	@Insert("INSERT INTO syspara(parameter,value) VALUES(#{parameter}, #{value})")
	void insert(Syspara syspara);
	
	@Delete("DELETE FROM syspara WHERE parameter=#{parameter}")
	void deleteByMatchDate(String parameter);
}