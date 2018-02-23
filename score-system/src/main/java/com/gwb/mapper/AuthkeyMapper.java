package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.gwb.entity.Authkey;

public interface AuthkeyMapper {
	
	@Select("SELECT * FROM authkey")
	List<Authkey> getAll();
	
	@Select("SELECT * FROM authkey WHERE key = #{key}")
	Authkey getOne(String key);
}