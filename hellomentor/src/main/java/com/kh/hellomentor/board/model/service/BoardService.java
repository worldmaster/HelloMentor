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
    public List<Reply> selectFreeDetailReply(int postNo);
    
    //5-3. 자유게시판 글 작성
    public int insertFree(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;
    public int insertFree2(int postNo);
    
    //6. 지식인 조회 (메인)
    public List<Board> selectKnowledgeList();
    public List<Knowledge> selectKnowledgeList2();
    public List<Answer> selectKnowledgeList3();
    
    //6-1. 지식인 상세 조회
    public Board selectKnowledgeDetail(int postNo);
    public Knowledge selectKnowledgeDetail2(int postNo);
    public List<Board> selectKnowledgeDetailAnswer(int postNo);
    

    
    





    //------------------------------정승훈-----------------------------------------
    List<Board> selectStudyList(int currentPage, Map<String, Object> paramMap);

    List<StudyApplicant> selectPepleList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectRecruitmentCount(Map<String, Object> paramMap);

    int insertStudy(Board b);

    Board selectDetailStudy(int postNo);

    int studyDetailApplicant(int postNo);

    List<Reply> selectReplyList(int postNo);


}
