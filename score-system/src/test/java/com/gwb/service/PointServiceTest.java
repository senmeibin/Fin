package com.gwb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwb.entity.Match;
import com.gwb.entity.Member;
import com.gwb.entity.Point;
import com.gwb.entity.Syspara;
import com.gwb.mapper.MatchMapper;
import com.gwb.mapper.MemberMapper;
import com.gwb.mapper.PointMapper;
import com.gwb.mapper.SysparaMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointServiceTest {

	@Autowired
	private PointService pointService;
	
	@Autowired
	private MatchMapper matchMapper;
	
	@Autowired
	private PointMapper pointMapper;

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private SysparaMapper sysparaMapper;
	
	//通常テスト
	@Test
	public void testCalulatePoints() throws Exception {
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20180101",3,4,1,2,21,18,0);
		Match match3 = new Match("20180101",1,2,3,4,21,19,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
		matchMapper.insert(match3);
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		
		Point point1 = new Point(1,"20170101",100,50,30,4000,5);
		Point point2 = new Point(2,"20170101",110,60,15,4100,4);
		Point point3 = new Point(3,"20170101",120,70,15,4200,3);
		Point point4 = new Point(4,"20170101",130,80,15,4300,2);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point3);
		        add(point4);
		    }
		};
		
		Map<Integer,Point> newPoints = pointService.caculatePoints(points, matches);
		
		System.out.println(newPoints);
		System.out.println(matches);
		matches = matchMapper.getByMatchDate("20180101");
		System.out.println(matches);
		
		matches.forEach(x -> matchMapper.delete(x.getId()));
	}
	
	//初回メンバーの場合のテスト
	@Test
	public void testCalulatePoints2() throws Exception {
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20180101",3,4,1,2,21,18,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		
		Point point1 = new Point(1,"20170101",100,50,30,4000,5);
		Point point2 = new Point(2,"20170101",110,60,15,4100,4);
		Point point3 = new Point(3,"20170101",120,70,15,4200,3);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point3);
		    }
		};
		
		Member member4 = memberMapper.getOne(4);
		if(member4==null) {
			member4 = new Member(4,"test",2000,1);
			memberMapper.insertWithId(member4);
		}
		
		Map<Integer,Point> newPoints = pointService.caculatePoints(points, matches);
		
		System.out.println(newPoints);
		System.out.println(matches);
		matches = matchMapper.getByMatchDate("20180101");
		System.out.println(matches);
		
		matches.forEach(x -> matchMapper.delete(x.getId()));
		memberMapper.delete(4);
	}
	
	//上限下限テスト
	@Test
	public void testCalulatePoints3() throws Exception {
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20180101",3,4,1,2,21,18,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		
		Point point1 = new Point(1,"20170101",100,50,30,100,5);
		Point point2 = new Point(2,"20170101",110,60,15,200,4);
		Point point3 = new Point(3,"20170101",120,70,15,4200,3);
		Point point4 = new Point(4,"20170101",130,80,15,4300,2);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point3);
		        add(point4);
		    }
		};
		
		Map<Integer,Point> newPoints = pointService.caculatePoints(points, matches);
		
		System.out.println(newPoints);
		System.out.println(matches);
		matches = matchMapper.getByMatchDate("20180101");
		System.out.println(matches);
		
		matches.forEach(x -> matchMapper.delete(x.getId()));
		pointMapper.deleteByMatchDate("20170101");
	}

	//通常テスト
	@Test
	public void testCreatePointResult() throws Exception {
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20180101",3,4,1,2,21,18,0);
		Match match3 = new Match("20180101",1,2,3,4,21,19,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
		matchMapper.insert(match3);
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		
		Point point1 = new Point(1,"20170101",100,50,30,4000,5);
		Point point2 = new Point(2,"20170101",110,60,15,4100,4);
		Point point3 = new Point(3,"20170101",120,70,15,4200,3);
		Point point4 = new Point(4,"20170101",130,80,15,4300,2);
		Point point5 = new Point(5,"20170101",140,90,20,4400,1);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point3);
		        add(point4);
		        add(point5);
		    }
		};
		
		Map<Integer,Point> newPoints = pointService.caculatePoints(points, matches);
		pointService.createPointResult(points,newPoints,"20180101");
		
		System.out.println(newPoints);
		System.out.println(matches);
		List<Point> newPointList = pointMapper.getByMatchDateSortRank("20180101");
		System.out.println(newPointList);
		
		matches.forEach(x -> matchMapper.delete(x.getId()));
		pointMapper.deleteByMatchDate("20170101");
	}
	
	//通常テスト
	@Test
	public void testprocessMatchResults() throws Exception {
		sysparaMapper.insert(new Syspara("last_batch_date","20171230"));
		
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20180101",3,4,1,2,21,18,0);
		Match match3 = new Match("20180101",1,2,3,4,21,19,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
		matchMapper.insert(match3);

		Point point1 = new Point(1,"20171231",100,50,30,4000,5);
		Point point2 = new Point(2,"20171231",110,60,15,4100,4);
		Point point4 = new Point(4,"20171231",130,80,15,4300,2);
		Point point5 = new Point(5,"20171231",140,90,20,4400,1);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point4);
		        add(point5);
		    }
		};
		points.forEach(x -> pointMapper.insert(x));
		
		memberMapper.insertWithId(new Member(1,"aa",2000,1));
		memberMapper.insertWithId(new Member(2,"bb",2000,1));
		memberMapper.insertWithId(new Member(3,"cc",2000,1));
		memberMapper.insertWithId(new Member(4,"dd",2000,0));
		
		pointService.processMatchResults();
		
		System.out.println(sysparaMapper.getOneForUpdate("last_batch_date"));
		
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		System.out.println(matches);
		
		System.out.println(pointMapper.getByMatchDateSortRank("20171231"));
		System.out.println(pointMapper.getByMatchDateSortRank("20180101"));
		
		memberMapper.delete(1);
		memberMapper.delete(2);
		memberMapper.delete(3);
		memberMapper.delete(4);
		matches.forEach(x -> matchMapper.delete(x.getId()));
		pointMapper.deleteByMatchDate("20171231");
	}
	
	@Test
	public void testcaculateOneMatchPoint() throws Exception {
		Match match1 = new Match("20180102",1,2,3,4,21,19,0);
		matchMapper.insert(match1);
		
		Point point1 = new Point(1,"20180101",100,50,30,4000,5);
		Point point2 = new Point(2,"20180101",110,60,15,4100,4);
		Point point4 = new Point(4,"20180101",130,80,15,4300,2);
		Point point5 = new Point(3,"20180101",140,90,20,4400,1);
		List<Point> points = new ArrayList<Point>(){
			private static final long serialVersionUID = 1L;
			{
		        add(point1);
		        add(point2);
		        add(point4);
		        add(point5);
		    }
		};
		points.forEach(x -> pointMapper.insert(x));
		
		System.out.println("matchPoint:"+pointService.caculateOneMatchPoint(match1));
		
		matchMapper.delete(match1.getId());
		pointMapper.deleteByMatchDate("20180101");
	}
}