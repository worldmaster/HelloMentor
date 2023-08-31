package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

   Member loginUser(Member m);

   int insertMember(Member m);

    List<Map<String, Object>> getFollowList(int userNo);
}