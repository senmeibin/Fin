package com.gwb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwb.entity.Member;
import com.gwb.mapper.MemberMapper;
import com.gwb.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/member/getAll")
	public List<Member> getAll() {
		List<Member> members=memberService.getAll();
		return members;
	}
	
	@RequestMapping("/member/getAllActivated")
	public List<Member> getAllActived() {
		List<Member> members=memberService.getAllActived();
		return members;
	}
	
//    @RequestMapping("/member/getOne")
//    public Member getOne(int id) {
//    	Member user=memberMapper.getOne(id);
//        return user;
//    }
    
    @RequestMapping("/member/add")
    public Member add(@RequestBody Member member) {
    	memberService.add(member);
    	return member;
    }
    
    @RequestMapping("/member/edit")
    public void update(@RequestBody Member member) {
    	memberService.edit(member);
    }
//    
//    @RequestMapping(value="/member/delete")
//    public void delete(int id) {
//    	memberMapper.delete(id);
//    }
}