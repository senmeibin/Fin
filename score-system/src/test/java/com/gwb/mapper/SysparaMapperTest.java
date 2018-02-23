package com.gwb.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwb.entity.Syspara;
import com.gwb.mapper.SysparaMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysparaMapperTest {

	@Autowired
	private SysparaMapper sysparaMapper;

	@Test
	public void testQuery() throws Exception {
		List<Syspara> paras = sysparaMapper.getAll();
		System.out.println(paras.toString());
	}
}