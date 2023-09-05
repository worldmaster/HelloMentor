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
import com.kh.hellomentor.board.model.vo.BoardType;
import com.kh.hellomentor.board.model.vo.Free;
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
	
    public List<Board> selectNoticeList(int currentPage) {
        int offset = (currentPage - 1) * 5;
        int limit = 5;

        RowBounds rowBounds = new RowBounds(offset, limit);

        return session.selectList("boardMapper.selectNoticeList", rowBounds);
    }

    public int selectNoticeListCount() {
        return session.selectOne("boardMapper.selectNoticeListCount");
    }

    // 2. 1:1문의 등록 (미완성)
    public int insertInquiry(Board board) {
    	int result = 0;
    	result = session.insert("boardMapper.insertInquiry" , board);
		
		if(result > 0) {
			result = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
		}
		
		return result;
    }

    public int insertInquiryAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertInquiryAttachment", list);
    }
    
    // 3. 문의내역 조회
	
    public List<Board> selectInquiryList() {
        return session.selectList("boardMapper.selectInquiryList");
    }
    public List<Inquiry> selectInquiryList2() {
        return session.selectList("boardMapper.selectInquiryList2");
    }
    
    // 4. 문의내역 상세 조회
    public Board selectinquirydetail(int postNo) {
        return session.selectOne("boardMapper.selectinquirydetail");
    }
    public Inquiry selectinquirydetail2(int postNo) {
        return session.selectOne("boardMapper.selectinquirydetail");
    }
    
    // 5. 자유게시판 조회
	
    public List<Board> selectFreeList() {
        return session.selectList("boardMapper.selectFreeList");
    }
    public List<Free> selectFreeList2() {
        return session.selectList("boardMapper.selectFreeList2");
    }
    //6. 지식인 조회
    //이찬우 구역 끝
}
