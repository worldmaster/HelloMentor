package com.kh.hellomentor.member.model.dao;

import com.kh.hellomentor.board.model.vo.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    @Autowired
    private SqlSessionTemplate session;
    public List<Board> getPostsByUserNo(int userNo) {
        return session.selectList("member.getPostsByUserNo",userNo);
    }
}
