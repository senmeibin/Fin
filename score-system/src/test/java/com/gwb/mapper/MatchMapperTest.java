package com.gwb.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwb.entity.Match;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchMapperTest {

	@Autowired
	private MatchMapper matchMapper;
	
	@Test
	public void testInsert() throws Exception {
		Match match1 = new Match("20180101",1,2,3,4,21,19,0);
		Match match2 = new Match("20171201",4,3,2,1,21,19,0);
		matchMapper.insert(match1);
		matchMapper.insert(match2);
	}
	
	@Test
	public void testQuery() throws Exception {
		List<Match> matches = matchMapper.getByMatchDate("20180101");
		System.out.println(matches.toString());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Match match = matchMapper.getOne(1);
		match.setPoints(20);
		matchMapper.update(match);
		Match match2 = matchMapper.getOne(1);
		Assert.assertTrue((20==match2.getPoints()));
	}

}