package com.kh.hellomentor.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hellomentor.member.model.dao.MemberDao;
import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member loginUser(Member m) {
        return memberDao.loginUser(m);
    }

    @Override
    public int insertMember(Member m) {
        return memberDao.insertMember(m);
    }

    @Override
    public List<Map<String, Object>> getFollowList(int userNo) {
        return memberDao.getFollowList(userNo);
    }
}

