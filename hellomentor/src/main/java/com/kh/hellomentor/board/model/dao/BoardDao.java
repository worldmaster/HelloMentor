package com.kh.hellomentor.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Inquiry;
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


    // 이찬우 구역 시작
    // 1. 공지사항 게시글 조회, 글 갯수 조회 (페이징바) (미완성)
    public List<Board> selectNoticeList(int currentPage, Map<String, Object> paramMap) {
        int offset = (currentPage - 1) * 5;
        int limit = 5;

        RowBounds rowBounds = new RowBounds(offset, limit);

        return session.selectList("boardMapper.selectNoticeList", paramMap, rowBounds);
    }

    public int selectListCount(Map<String, Object> paramMap) {
        return session.selectOne("boardMapper.selectListCount", paramMap);
    }

    // 2. 1:1문의 등록 (미완성)
    public int insertInqueryBoard(Board board) {
        return session.insert("boardMapper.insertInqueryBoard", board);
    }

    public int insertInquiry(Inquiry inquery) {
        return session.insert("boardMapper.insertInquiry", inquery);
    }

    public int insertInquiryAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertInquiryAttachment", list);
    }
    //이찬우 구역 끝
}
