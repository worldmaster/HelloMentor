package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
import com.kh.hellomentor.board.model.vo.Reply;
import com.kh.hellomentor.matching.model.vo.StudyApplicant;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);

    List<Reply> getReplyByUserNo(int userNo);


    //이찬우 구역 시작
    //0. 조회수 증가
    public int increaseCount(int postNo);
    
    //0-1. FAQ 삭제
    public int deletePost(int postNo);
    
    //1. 공지사항 목록 select
    public List<Board> selectNoticeList();
    
    //1-1. 공지사항 조회 (상세)
    public Board selectNoticeDetail(int postNo);

    //2. FAQ 조회
    public List<Board> selectFaqList();
    

    //3. 1:1문의 작성
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;
    public int insertInquiry2(Inquiry inquiry);
    
    //4. 문의내역 조회 (메인)
    public List<Board> selectInquiryList(int userNo);
    public List<Inquiry> selectInquiryList2(int userNo);
    
    //4-1. 문의내역 조회 (상세)
    public Board selectInquiryDetail(int postNo);
    public Inquiry selectInquiryDetail2(int postNo);
    
    //5. 자유게시판 조회
    public List<Board> selectFreeList();
    public List<Free> selectFreeList2();
    
    //5-1. 자유게시판 조회 (화제글 3개)
    public List<Board> selectBestFreeList();
    public List<Free> selectBestFreeList2();
    
    //5-2. 자유게시판 상세 조회
    public Board selectFreeDetail(int postNo);
    public Free selectFreeDetail2(int postNo);
    
    //5-3. 자유게시판 글 작성
    public int insertFree(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;
    public int insertFree2(int postNo);
    
    //5-4. 자유게시판 댓글 삽입
    public int insertFreeReply(Reply reply);
    
	//5-5. 자유게시판 댓글 조회
	public List<Reply> selectFreeReplyList(int postNo);
	
	//5-6. 자유게시판 댓글 삭제
    public int deleteReply(int replyId);
    
    //5-7. 추천수 증가
    public int increaseUpvotes(int postNo);
    
    //6. 지식인 조회 (메인)
    public List<Board> selectKnowledgeList();
    public List<Knowledge> selectKnowledgeList2();
    public List<Answer> selectKnowledgeList3();
    
    //6-1. 지식인 상세 조회
    public Board selectKnowledgeDetail(int postNo);
    public Knowledge selectKnowledgeDetail2(int postNo);
    public List<Board> selectKnowledgeDetailAnswer(int postNo);
    
    //6-2. 지식인 답변 갯수
    public int selectKnowledgeAnswerCount(int postNo);
    
    //6-3. 지식인 질문 등록
    public int insertKnowledgeQuestion(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;
    public int insertKnowledgeQuestion2(Knowledge knowledge);
    
    //6-4. 지식인 답변 등록
    public int insertKnowledgeAnswer(Board board) throws Exception;
    public int insertKnowledgeAnswer2(Answer answer);
    
    





    //------------------------------정승훈-----------------------------------------
    List<Board> selectStudyList(int currentPage, Map<String, Object> paramMap);

    List<StudyApplicant> selectPepleList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap);

    int insertStudy(Board b);

    Board selectDetailStudy(int postNo);

    int studyDetailApplicant(int postNo);

    List<Reply> selectReplyList(int postNo);


    Board selectBoard(int postNo);

    int insertReport(Map<String, Object> reportInfo);
}
