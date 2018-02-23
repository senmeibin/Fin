package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gwb.entity.Member;

public interface MemberMapper {
	
	@Select("SELECT * FROM member")
	List<Member> getAll();
	
	@Select("SELECT * FROM member where active_flg=1")
	List<Member> getAllActived();
	
	@Select("SELECT * FROM member WHERE id = #{id}")
	Member getOne(int id);

	@Insert("INSERT INTO member(name,init_point,active_flg) VALUES(#{name}, #{init_point}, #{active_flg})")
	@Options(useGeneratedKeys=true , keyProperty="id" ,keyColumn="id")
	void insert(Member member);
	
	@Insert("INSERT INTO member(id,name,init_point,active_flg) VALUES(#{id},#{name}, #{init_point}, #{active_flg})")
	void insertWithId(Member member);

	@Update("UPDATE member SET name=#{name},init_point=#{init_point} ,active_flg=#{active_flg} WHERE id =#{id}")
	void update(Member member);
	
	@Update("UPDATE member SET active_flg=1 WHERE id =#{id} AND active_flg=0")
	void Activate(int id);

	@Delete("DELETE FROM member WHERE id =#{id}")
	void delete(int id);
}