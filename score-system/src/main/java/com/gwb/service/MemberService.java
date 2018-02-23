package com.gwb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwb.entity.Member;
import com.gwb.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public List<Member> getAll() {
		return memberMapper.getAll();
	}
	
	public List<Member> getAllActived() {
		return memberMapper.getAllActived();
	}
	
	public void add(Member member) {
		memberMapper.insert(member);
	}

	public void edit(Member member) {
		memberMapper.update(member);
	}
}