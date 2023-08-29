package com.kh.hellomentor.member.model.service;

import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.member.model.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Board> getPostsByUserNo(int userNo) {
        return memberDao.getPostsByUserNo(userNo);
    }
}


