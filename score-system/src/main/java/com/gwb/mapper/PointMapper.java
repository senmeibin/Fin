package com.gwb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.gwb.entity.Member;
import com.gwb.entity.Point;
import com.gwb.entity.WinPer;

public interface PointMapper {
	
	@Select("SELECT distinct match_date FROM point ORDER BY match_date DESC")
	List<String> getAllMatchDate();
	
	@Select("SELECT * FROM point WHERE match_date=#{match_date} ORDER BY id")
	List<Point> getByMatchDateSortId(String match_date);
	
	@Select("SELECT * FROM point WHERE match_date=#{match_date} ORDER BY rank")
	List<Point> getByMatchDateSortRank(String match_date);
	
	@Select("SELECT a.* FROM point a INNER JOIN (SELECT id, max(match_date) AS maxmatchdate FROM point WHERE match_date < #{match_date} GROUP BY id) AS b on a.id =b.id AND a.match_date = b.maxmatchdate ORDER BY a.id")
	List<Point> getLastPointByMatchDateSortId(String match_date);
	
	@Select("SELECT distinct a.id ,b.name FROM point a  INNER JOIN member b ON a.id = b.id WHERE a.match_date >= #{match_date} AND (a.win_match>0 OR a.loss_match>0) order by a.id")
	List<Member> getRecentMember(String match_date);
	
	@Select("select p.id , m.name ,sum(win_match) as total_win_match,sum(loss_match) as total_loss_match,(sum(win_match)+sum(loss_match)) as total_match,sum(win_match)*1.0/NULLIF(sum(win_match)+sum(loss_match),0)  as win_per from point as p  inner join (select id from point where match_date = #{match_date}) as q on p.id = q.id inner join member as m on p.id = m.id where match_date <= #{match_date} group by p.id having total_match > 10 order by win_per desc ;")
	List<WinPer> getWinPerByMatchDateSortRank(String match_date);

	@Insert("INSERT INTO point(id,match_date,win_match,loss_match,get_points,total_points,rank) VALUES( #{id}, #{match_date}, #{win_match}, #{loss_match}, #{get_points}, #{total_points}, #{rank})")
	void insert(Point point);

	@Delete("DELETE FROM point WHERE match_date=#{match_date}")
	void deleteByMatchDate(String match_date);
}