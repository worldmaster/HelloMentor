package com.kh.hellomentor.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
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
    // 1. 공지사항 게시글 조회
	
    public List<Board> selectNoticeList() {
        return session.selectList("boardMapper.selectNoticeList");
    }  
    // 1-2. 공지사항 상세 조회
    public Board selectNoticeDetail(int postNo) {
        return session.selectOne("boardMapper.selectNoticeDetail",postNo);
    }
    
    //2. FAQ 조회
    public List<Board> selectFaqList() {
        return session.selectList("boardMapper.selectFaqList");
    }

    //3. 1:1문의 등록
    public int insertInquiry(Board board) {
    	int result = 0;
    	int postNo = 0;
    	result = session.insert("boardMapper.insertInquiry" , board);
		
		if(result > 0) {
			postNo = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
			System.out.println(postNo);
		}
		
		return postNo;
    }
    public int insertInquiry2(Inquiry inquiry) {
    	return session.insert("boardMapper.insertInquiry2" , inquiry);
    }

    public int insertInquiryAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertInquiryAttachment", list);
    }
    
    //4. 문의내역 조회
	
    public List<Board> selectInquiryList(int userNo) {
        return session.selectList("boardMapper.selectInquiryList",userNo);
    }
    public List<Inquiry> selectInquiryList2(int userNo) {
        return session.selectList("boardMapper.selectInquiryList2",userNo);
    }
    
    //4-1. 문의내역 상세 조회
    public Board selectInquiryDetail(int postNo) {
        return session.selectOne("boardMapper.selectInquiryDetail");
    }
    public Inquiry selectInquiryDetail2(int postNo) {
        return session.selectOne("boardMapper.selectInquiryDetail2");
    }
    
    //5. 자유게시판 조회
	
    public List<Board> selectFreeList() {
        return session.selectList("boardMapper.selectFreeList");
    }
    public List<Free> selectFreeList2() {
        return session.selectList("boardMapper.selectFreeList2");
    }
    //5-1. 자유게시판 조회 (화제글 3개)
    public List<Board> selectBestFreeList() {
        return session.selectList("boardMapper.selectBestFreeList");
    }
    public List<Free> selectBestFreeList2() {
        return session.selectList("boardMapper.selectBestFreeList2");
    }
    //5-2. 자유게시판 상세 조회
    public Board selectFreeDetail(int postNo) {
        return session.selectOne("boardMapper.selectFreeDetail",postNo);
    }
    public Free selectFreeDetail2(int postNo) {
        return session.selectOne("boardMapper.selectFreeDetail2",postNo);
    }
    public List<Reply> selectFreeDetailReply(int postNo) {
        return session.selectList("boardMapper.selectFreeDetailReply",postNo);
    }
    //5-3. 자유게시판 글 작성
    //3. 1:1문의 등록
    public int insertFree(Board board) {
    	int result = 0;
    	int postNo = 0;
    	result = session.insert("boardMapper.insertFree" , board);
		
		if(result > 0) {
			postNo = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
			System.out.println(postNo);
		}
		
		return postNo;
    }
    public int insertFree2(int postNo) {
    	return session.insert("boardMapper.insertFree2" , postNo);
    }

    public int insertFreeAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertFreeAttachment", list);
    }
    
    //6. 지식인 조회 (메인)
    public List<Board> selectKnowledgeList() {
        return session.selectList("boardMapper.selectKnowledgeList");
    }
    public List<Knowledge> selectKnowledgeList2() {
        return session.selectList("boardMapper.selectKnowledgeList2");
    }
    public List<Answer> selectKnowledgeList3() {
        return session.selectList("boardMapper.selectKnowledgeList3");
    }
    
    // 6-2. 자유게시판 상세 조회
    public Board selectKnowledgeDetail(int postNo) {
        return session.selectOne("boardMapper.selectKnowledgeDetail",postNo);
    }
    public Knowledge selectKnowledgeDetail2(int postNo) {
        return session.selectOne("boardMapper.selectKnowledgeDetail2",postNo);
    }
    public List<Board> selectKnowledgeDetailAnswer(int postNo) {
        return session.selectList("boardMapper.selectKnowledgeDetailAnswer",postNo);
    }
    
 
    //이찬우 구역 끝
}
