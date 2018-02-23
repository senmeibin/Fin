package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gwb.entity.Match;

public interface MatchMapper {
	
	@Select("SELECT * FROM match WHERE match_date=#{match_date} ORDER BY id")
	List<Match> getByMatchDate(String match_date);
	
	@Select("SELECT * FROM match WHERE id = #{id}")
	Match getOne(int id);

	@Insert("INSERT INTO match(match_date,winner1, winner2, losser1, losser2, win_score, loss_score, points) VALUES(#{match_date}, #{winner1}, #{winner2}, #{losser1}, #{losser2}, #{win_score}, #{loss_score}, #{points})")
	void insert(Match match);

	@Update("UPDATE match SET match_date=#{match_date},winner1=#{winner1} ,winner2=#{winner2} ,losser1=#{losser1},losser2=#{losser2} ,win_score=#{win_score},loss_score=#{loss_score},points=#{points} WHERE id =#{id}")
	void update(Match match);

	@Delete("DELETE FROM match WHERE id =#{id}")
	void delete(int id);
}