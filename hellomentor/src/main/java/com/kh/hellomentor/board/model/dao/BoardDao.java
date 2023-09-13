package com.kh.hellomentor.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.matching.model.vo.StudyApplicant;
import org.apache.ibatis.session.RowBounds;
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
    // 0. 조회수 증가
    public int increaseCount(int postNo) {
    	return session.update("boardMapper.increaseCount",postNo);
    }
    //0-1. 게시글 삭제
    public int deletePost(int postNo) {
        return session.update("boardMapper.deletePost",postNo);
    }
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
        return session.selectOne("boardMapper.selectInquiryDetail",postNo);
    }
    public Inquiry selectInquiryDetail2(int postNo) {
        return session.selectOne("boardMapper.selectInquiryDetail2",postNo);
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

    //5-3. 자유게시판 글 작성
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
    //5-4. 자유게시판 댓글 삽입
    public int insertFreeReply(Reply reply) {
		return session.insert("boardMapper.insertFreeReply" , reply);
	}
	//5-5. 자유게시판 댓글 조회
    public List<Reply> selectFreeReplyList(int postNo) {
        return session.selectList("boardMapper.selectFreeReplyList",postNo);
    }
    //5-6. 자유게시판 댓글 삭제
    public int deleteReply(int replyNo) {
        return session.update("boardMapper.deleteReply",replyNo);
    }
    // 5-7. 자유게시판 추천수 증가
    public int increaseUpvotes(int postNo) {
    	return session.update("boardMapper.increaseUpvotes",postNo);
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
    
    // 6-1. 지식인 상세 조회
    public Board selectKnowledgeDetail(int postNo) {
        return session.selectOne("boardMapper.selectKnowledgeDetail",postNo);
    }
    public Knowledge selectKnowledgeDetail2(int postNo) {
        return session.selectOne("boardMapper.selectKnowledgeDetail2",postNo);
    }
    public List<Board> selectKnowledgeDetailAnswer(int postNo) {
        return session.selectList("boardMapper.selectKnowledgeDetailAnswer",postNo);
    }
    
    // 6-2. 지식인 답변 갯수
    public int selectKnowledgeAnswerCount(int postNo) {
		return session.selectOne("boardMapper.selectKnowledgeAnswerCount", postNo);
	}
    
    //6-3. 지식인 질문 등록
    public int insertKnowledgeQuestion(Board board) {
    	int result = 0;
    	int postNo = 0;
    	result = session.insert("boardMapper.insertKnowledgeQuestion" , board);
		
		if(result > 0) {
			postNo = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
			System.out.println(postNo);
		}
		
		return postNo;
    }
    public int insertKnowledgeQuestion2(Knowledge knowledge) {
    	return session.insert("boardMapper.insertKnowledgeQuestion2" , knowledge);
    }

    public int insertKnowledgeAttachment(List<Attachment> list) {
        return session.insert("boardMapper.insertKnowledgeAttachment", list);
    }
    //6-4. 지식인 답변 등록
    public int insertKnowledgeAnswer(Board board) {
    	int result = 0;
    	int postNo = 0;
    	result = session.insert("boardMapper.insertKnowledgeAnswer" , board);
		
		if(result > 0) {
			postNo = board.getPostNo();
			// 게시글 삽입 성공시 selectKey태그를 사용하여 셋팅한 boardNo값을 b에 담아줌.
			System.out.println(postNo);
		}
		
		return postNo;
    }
    public int insertKnowledgeAnswer2(Answer answer) {
    	return session.insert("boardMapper.insertKnowledgeAnswer2" , answer);
    }
    //이찬우 구역 끝


    //정승훈 구역
    public List<Board> selectStudyList(int currentPage, Map<String, Object> paramMap) {
        int offset = (currentPage - 1) * 5; //몇개의 페이지를 나타낼건지를 나타냄
        int limit = 5; //총 몇개의 게시글을 가지고 올것인지 나타냄

        RowBounds rowBounds = new RowBounds(offset, limit);

        return session.selectList("boardMapper.selectStudyList", paramMap, rowBounds);
    }




    public List<StudyApplicant> selectPepleList(Map<String, Object> paramMap) {
        return session.selectList("boardMapper.selectPepleList", paramMap);
    }

    public List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap) {
        return session.selectList("boardMapper.selectRecruitmentCount", paramMap);
    }

    public int insertStudy(Board b) {
        return session.insert("boardMapper.insertStudy",b);
    }

    public Board selectDetailStudy(int postNo) {
        return session.selectOne("boardMapper.selectDetailStudy",postNo);
    }

    public int studyDetailApplicant(int postNo) {
        return session.selectOne("boardMapper.studyDetailApplicant",postNo);
    }

    public List<Reply> selectReplyList(int postNo) {
        return session.selectList("boardMapper.selectReplyList",postNo);
    }




    //------------------------------정승훈-----------------------------------------



}
