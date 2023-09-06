package com.kh.hellomentor.board.model.service;

import java.util.List;

import com.kh.hellomentor.board.model.vo.Answer;
import com.kh.hellomentor.board.model.vo.Attachment;
import com.kh.hellomentor.board.model.vo.Board;
import com.kh.hellomentor.board.model.vo.Free;
import com.kh.hellomentor.board.model.vo.Inquiry;
import com.kh.hellomentor.board.model.vo.Knowledge;
import com.kh.hellomentor.board.model.vo.Reply;

public interface BoardService {
    List<Board> getPostsByUserNo(int userNo);

    List<Reply> getReplyByUserNo(int userNo);


    //이찬우 구역 시작
    //1. 공지사항 목록 select
    public List<Board> selectNoticeList();


    //2. 1:1문의 작성
    public int insertInquiry(Board board,  List<Attachment> list, String serverPath, String webPath) throws Exception;

    //3. 문의내역 조회 (메인)
    public List<Board> selectInquiryList();
    public List<Inquiry> selectInquiryList2();
    
    //3-1. 문의내역 조회 (상세)
    public List<Board> selectinquirydetail(int postNo);
    public List<Inquiry> selectinquirydetail2(int postNo);
    
    //4. 자유게시판 조회
    public List<Board> selectFreeList();
    public List<Free> selectFreeList2();
    
    //4-1. 자유게시판 조회 (화제글 3개)
    public List<Board> selectBestFreeList();
    public List<Free> selectBestFreeList2();
    
    //5. 지식인 조회 (메인)
    public List<Board> selectKnowledgeList();
    public List<Knowledge> selectKnowledgeList2();
    public List<Answer> selectKnowledgeList3();
    //6. FAQ 조회
    public List<Board> selectFaqList();
    
    
}
