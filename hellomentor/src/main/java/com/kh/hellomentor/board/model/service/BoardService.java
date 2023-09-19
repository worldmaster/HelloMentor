package com.kh.hellomentor.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

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
    
    //0-1. 글 삭제
    public int deletePost(int postNo);
    
    //1. 공지사항 목록 select
    public int selectNoticeCount();
    public int searchNoticeCount(String ntkind, String keyword);
    
    public List<Board> selectNoticeList(int page, int pageSize);
    public List<Board> searchNoticeList(String ntkind, String keyword, int page, int pageSize);
    
    //1-1. 공지사항 조회 (상세)
    public Board selectNoticeDetail(int postNo);

    //2. FAQ 조회
    public int selectFaqCount();
    public int searchFaqCount(String faqkind, String keyword);
    public List<Board> selectFaqList(int page, int pageSize);
    public List<Board> searchFaqList(String faqkind, String keyword, int page, int pageSize);
    

    //3. 1:1문의 작성
    public int insertInquiry(Board board,  List<Attachment> list,  String webPath) throws Exception;
    public int insertInquiry2(Inquiry inquiry);
    
    //4. 문의내역 조회 (메인)
    
    public int selectInquiryCount();
    public List<Board> selectInquiryList(int userNo, int page, int pageSize);
    public List<Inquiry> selectInquiryList2(int userNo, int page, int pageSize);

    
    //4-1. 문의내역 조회 (상세)
    public Board selectInquiryDetail(int postNo);
    public Inquiry selectInquiryDetail2(int postNo);
    
    //5. 자유게시판 조회
    public int selectFreeCount();
    public int searchFreeCount(String freekind, String keyword);
    public List<Board> selectFreeList(int page, int pageSize);
    public List<Free> selectFreeList2(int page, int pageSize);
    public List<Board> searchFreeList(String freekind, String keyword, int page, int pageSize);
    public List<Free> searchFreeList2(String freekind, String keyword, int page, int pageSize);
    
    //5-1. 자유게시판 조회 (화제글 3개)
    public List<Board> selectBestFreeList();
    public List<Free> selectBestFreeList2();
    
    //5-2. 자유게시판 상세 조회
    public Board selectFreeDetail(int postNo);
    public Free selectFreeDetail2(int postNo);
    public List<Attachment> selectAttachment(int postNo);
    
    //5-3. 자유게시판 글 작성
    public int insertFree(Board board,  List<Attachment> list, String webPath) throws Exception;
    public int insertFree2(int postNo);
    
    //5-4. 자유게시판 댓글 삽입
    public int insertFreeReply(Reply reply);
    
	//5-5. 자유게시판 댓글 조회
	public List<Reply> selectFreeReplyList(int postNo);
	
	//5-6. 자유게시판 댓글 삭제
    public int deleteReply(int replyId);
    
    //5-7. 자유게시판 추천수 증가
    public int increaseUpvotes(int postNo);
    
    //5-8. 자유게시판 수정
    public int updateFree(Board b, List<MultipartFile> list, String wholePath, String webPath) throws Exception;
    
    //6. 지식인 조회 (메인)
    public int selectKnowledgeCount();
    public int searchKnowledgeCount(String knowledgekind, String keyword, String best, String accepted);
    public List<Board> selectKnowledgeList(int page, int pageSize);
    public List<Knowledge> selectKnowledgeList2(int page, int pageSize);
    public List<Board> searchKnowledgeList(String knowledgekind, String keyword, String best, String accepted, int page, int pageSize);
    public List<Knowledge> searchKnowledgeList2(String knowledgekind, String keyword, String best, String accepted, int page, int pageSize);
    public List<Answer> searchKnowledgeList3(String knowledgekind, String keyword, int page, int pageSize);
    
    //6-1. 지식인 상세 조회
    public Board selectKnowledgeDetail(int postNo);
    public Knowledge selectKnowledgeDetail2(int postNo);
    public List<Board> selectKnowledgeDetailAnswer(int postNo);
    public int selectKnowledgeAccepted(int postNo);
    
    //6-2. 지식인 답변 갯수
    public int selectKnowledgeAnswerCount(int postNo);
    
    //6-3. 지식인 질문 등록
    public int insertKnowledgeQuestion(Board board,  List<Attachment> list,  String webPath) throws Exception;
    public int insertKnowledgeQuestion2(Knowledge knowledge);
    
    //6-4. 지식인 답변 등록
    public int insertKnowledgeAnswer(Board board) throws Exception;
    public int insertKnowledgeAnswer2(Answer answer);
    
    //6-6. 지식인 질문 수정
    public int updateKnowledgeQuestion(Board b, Knowledge k, List<MultipartFile> list, String wholePath, String webPath) throws Exception;
    
    //6-8. 지식인 채택
    public int updateknowledgeAcceped(int postNo);
   
    //6-9. 지식인 추천수 증가
    public int increaseKnowledgeUpvotes(int postNo);

    //6-10. 지식인 답변 수정
    public int knowledgeAnswerUpdate(Board b) throws Exception;

    
    





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
