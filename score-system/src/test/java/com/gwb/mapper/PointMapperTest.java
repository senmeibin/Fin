package com.gwb.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwb.entity.Point;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointMapperTest {

	@Autowired
	private PointMapper pointMapper;
	
	@Test
	public void testInsert() throws Exception {
		Point point1 = new Point(1,"20180101",100,50,30,4000,3);
		Point point2 = new Point(2,"20180101",80,70,15,3300,10);
		Point point3 = new Point(1,"20170101",90,60,30,4000,3);
		Point point4 = new Point(2,"20170101",70,80,15,3300,10);
		Point point5 = new Point(3,"20170101",10,20,15,3300,10);
		pointMapper.insert(point1);
		pointMapper.insert(point2);
		pointMapper.insert(point3);
		pointMapper.insert(point4);
		pointMapper.insert(point5);
		
		System.out.println(pointMapper.getLastPointByMatchDateSortId("20180101").toString());
	}
	
	@Test
	public void testQuery() throws Exception {
		List<Point> pointes = pointMapper.getByMatchDateSortId("20180101");
		System.out.println(pointes.toString());
	}
	
	@Test
	public void testDelete() throws Exception {
		pointMapper.deleteByMatchDate("20180101");
		Assert.assertTrue((0==pointMapper.getByMatchDateSortId("20180101").size()));
	}
}