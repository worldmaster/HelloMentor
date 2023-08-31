package com.kh.hellomentor.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.member.controller.MemberController;

@Repository
public class BoardDao {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private SqlSessionTemplate session;

    public List<Board> getPostsByUserNo(int userNo) {
        return session.selectList("boardMapper.getMyPost", userNo);
    }

    public List<Reply> getReplyByUserNo(int userNo) {
        return session.selectList("boardMapper.getMyReply", userNo);
    }

    
    // 공지사항 게시글 조회, 글 갯수 조회 (페이징바)
    public List<Board> selectNoticeList(int currentPage, Map<String , Object> paramMap){
		int offset = (currentPage -1) * 5;
		int limit  = 5;
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		return session.selectList("boardMapper.selectNoticeList" , paramMap , rowBounds);
	}
	
	public int selectListCount(Map<String , Object> paramMap) {
		return session.selectOne("boardMapper.selectListCount", paramMap);
	}
}
